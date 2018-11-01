package co.slandow.caboose.service.metadata.impl

import co.slandow.caboose.exception.InvalidWorkflowException
import co.slandow.caboose.model.metadata.WorkflowDef
import co.slandow.caboose.service.metadata.WorkflowDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service

import javax.validation.Validation

import static co.slandow.caboose.util.MongoUtil.buildPagedQuery
import static co.slandow.caboose.util.MongoUtil.buildQuery

@Service
class WorkflowDefServiceImpl implements WorkflowDefService {

    @Autowired
    private MongoOperations repo

    private final validator = Validation.buildDefaultValidatorFactory().validator

    WorkflowDef getWorkflowDef(String workflowDefName) {
        return repo.find(buildQuery(["name": workflowDefName]), WorkflowDef).max({it.version})
    }

    List<WorkflowDef> getWorkflowDefs() {
        return repo.findAll(WorkflowDef).sort({ a, b -> b.version - a.version })
    }

    List<WorkflowDef> getWorkflowDefs(int page, int size) {
        return repo.find(buildPagedQuery(page, size), WorkflowDef)
    }

    void saveWorkflowDef(WorkflowDef workflowDef) {
        // Validate
        final errors = validator.validate(workflowDef)
        if (errors) {
            throw new InvalidWorkflowException("${errors[0].propertyPath} ${errors[0].message})")
        }

        // Persist
        try {
            repo.insert(workflowDef)
        } catch (DuplicateKeyException ignore) {
            throw new InvalidWorkflowException("Version ${workflowDef.version} of \"${workflowDef.name}\" already esists.")
        }
    }
}

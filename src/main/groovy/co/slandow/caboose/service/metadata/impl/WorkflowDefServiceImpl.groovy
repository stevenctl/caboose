package co.slandow.caboose.service.metadata.impl

import co.slandow.caboose.model.metadata.WorkflowDef
import co.slandow.caboose.repo.WorkflowDefRepo
import co.slandow.caboose.service.metadata.WorkflowDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

import javax.validation.Validation
import java.util.stream.Collectors

@Service
class WorkflowDefServiceImpl implements WorkflowDefService {

    @Autowired
    private WorkflowDefRepo repo

    private final validator = Validation.buildDefaultValidatorFactory().validator

    WorkflowDef getWorkflowDef(String workflowDefName) {
        return repo.findByName(workflowDefName).max { it.version }
    }

    List<WorkflowDef> getWorkflowDefs() {
        return repo.findAll().sort({ a, b -> b.version - a.version })
    }

    List<WorkflowDef> getWorkflowDefs(int page, int size) {
        return repo.findAll(new PageRequest(page, size)).stream().collect(Collectors.toList())
    }

    void saveWorkflowDef(WorkflowDef workflowDef) {
        final errors = validator.validate(workflowDef)
        if (errors)
            throw new IllegalArgumentException(errors[0].message)
        repo.save(workflowDef)
    }
}

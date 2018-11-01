package co.slandow.caboose.service.metadata.impl

import co.slandow.caboose.model.WorkflowDef
import co.slandow.caboose.repo.WorkflowDefRepo
import co.slandow.caboose.service.metadata.WorkflowDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

import javax.validation.Valid
import javax.validation.Validation
import javax.validation.Validator
import java.util.stream.Collectors

class WorkflowDefServiceImpl implements WorkflowDefService {

    @Autowired
    private WorkflowDefRepo repo

    private final validator =  Validation.buildDefaultValidatorFactory().validator

    WorkflowDef getWorkflowDef(String workflowDefName) {
        return repo.findByName(workflowDefName).max { it.version }
    }

    List<WorkflowDef> getWorkflowDefs() {
        return repo.findAll().sort({ a, b -> b.version - a.version })
    }

    List<WorkflowDef> getWorkflowDefs(int page, int size) {
        return repo.findAll(new PageRequest(page, size)).stream().collect(Collectors.toList())
    }

    void saveWorkflowDef(@Valid WorkflowDef workflowDef) {
        final errors = validator.validate(workflowDef)
        if (errors)
            throw new IllegalArgumentException(errors[0].message)
        repo.save(workflowDef)
    }
}

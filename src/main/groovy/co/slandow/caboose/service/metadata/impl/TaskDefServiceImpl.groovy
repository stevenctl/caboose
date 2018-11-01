package co.slandow.caboose.service.metadata.impl

import co.slandow.caboose.model.metadata.TaskDef
import co.slandow.caboose.repo.TaskDefRepo
import co.slandow.caboose.service.metadata.TaskDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

import javax.validation.Validation
import java.util.stream.Collectors

@Service
class TaskDefServiceImpl implements TaskDefService {

    @Autowired
    private TaskDefRepo repo

    private final validator = Validation.buildDefaultValidatorFactory().validator

    TaskDef getTaskDef(String taskDefName) {
        return repo.findByName(taskDefName)
    }

    List<TaskDef> getTaskDefs() {
        return repo.findAll()
    }

    List<TaskDef> getTaskDefs(int page, int size) {
        return repo.findAll(new PageRequest(page, size)).stream().collect(Collectors.toList())
    }

    void saveTaskDef(TaskDef taskDef) {
        final errors = validator.validate(taskDef)
        if (errors)
            throw new IllegalArgumentException(errors[0].message)
        repo.save(taskDef)
    }
}

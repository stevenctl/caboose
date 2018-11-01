package co.slandow.caboose.service.metadata.impl

import co.slandow.caboose.model.metadata.TaskDef
import co.slandow.caboose.service.metadata.TaskDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service

import javax.validation.Validation

import static co.slandow.caboose.util.MongoUtil.buildPagedQuery
import static co.slandow.caboose.util.MongoUtil.buildQuery

@Service
class TaskDefServiceImpl implements TaskDefService {

    @Autowired
    private MongoOperations repo

    private final validator = Validation.buildDefaultValidatorFactory().validator

    TaskDef getTaskDef(String taskDefName) {
        return repo.findOne(buildQuery([name: taskDefName]), TaskDef)
    }

    List<TaskDef> getTaskDefs() {
        return repo.findAll(TaskDef)
    }

    List<TaskDef> getTaskDefs(int page, int size) {
        return repo.find(buildPagedQuery(page, size), TaskDef)
    }

    void saveTaskDef(TaskDef taskDef) {
        final errors = validator.validate(taskDef)
        if (errors) {
            throw new IllegalArgumentException("${errors[0].propertyPath} ${errors[0].message})")
        }
        repo.findAndReplace(buildQuery([name: taskDef.name]), taskDef)
    }
}

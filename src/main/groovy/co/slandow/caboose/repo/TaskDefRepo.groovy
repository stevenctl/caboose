package co.slandow.caboose.repo

import co.slandow.caboose.model.metadata.TaskDef
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskDefRepo extends MongoRepository<TaskDef, String> {

    TaskDef findByName(String name)

}

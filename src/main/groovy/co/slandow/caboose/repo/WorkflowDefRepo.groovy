package co.slandow.caboose.repo

import co.slandow.caboose.model.WorkflowDef
import org.springframework.data.mongodb.repository.MongoRepository

interface WorkflowDefRepo extends MongoRepository<WorkflowDef, String> {

    List<WorkflowDef> findByName(String name)
    WorkflowDef findByNameAndVersion(String name, int version)


}

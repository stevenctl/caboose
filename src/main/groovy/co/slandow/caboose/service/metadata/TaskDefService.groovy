package co.slandow.caboose.service.metadata

import co.slandow.caboose.model.metadata.TaskDef

interface TaskDefService {

    TaskDef getTaskDef(String taskDefName)

    List<TaskDef> getTaskDefs()

    List<TaskDef> getTaskDefs(int page, int size)

    void saveTaskDef(TaskDef taskDef)

}
package co.slandow.caboose.controllers.metadata

import co.slandow.caboose.model.metadata.TaskDef
import co.slandow.caboose.service.metadata.TaskDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/metadata/taskdefs")
class TaskDefController {

    @Autowired
    private TaskDefService taskDefService

    @GetMapping
    List<TaskDef> getWorkflowDefinitions(
            @RequestParam(value = "page", defaultValue = "-1") int page,
            @RequestParam(value = "size", defaultValue = "-1") int size) {
        return page > -1 && size > -1 ? taskDefService.getTaskDefs(page, size)
                : taskDefService.getTaskDefs()
    }

    @GetMapping("/{name}")
    TaskDef getWorkflowDefinition(@PathVariable String name) {
        return taskDefService.getTaskDef(name)
    }

    @PutMapping
    void createWorkflowDefinition(@RequestBody TaskDef taskDef) {
        taskDefService.saveTaskDef(taskDef)
    }


}

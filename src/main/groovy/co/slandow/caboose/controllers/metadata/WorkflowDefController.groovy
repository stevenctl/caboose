package co.slandow.caboose.controllers.metadata

import co.slandow.caboose.model.metadata.WorkflowDef
import co.slandow.caboose.service.metadata.WorkflowDefService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/metadata/workflow")
class WorkflowDefController {

    @Autowired
    private WorkflowDefService workflowDefService

    @GetMapping
    List<WorkflowDef> getWorkflowDefinitions(
            @RequestParam(value = "page", defaultValue = "-1") int page,
            @RequestParam(value = "size", defaultValue = "-1") int size) {
        return page > -1 && size > -1 ? workflowDefService.getWorkflowDefs(page, size)
                : workflowDefService.getWorkflowDefs()
    }

    @GetMapping("/{name}")
    WorkflowDef getWorkflowDefinition(@PathVariable String name) {
        return workflowDefService.getWorkflowDef(name)
    }

    @PutMapping
    void createWorkflowDefinition(@RequestBody WorkflowDef workflowDef) {
        workflowDefService.saveWorkflowDef(workflowDef)
    }


}

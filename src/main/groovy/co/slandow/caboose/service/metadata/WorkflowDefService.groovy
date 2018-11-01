package co.slandow.caboose.service.metadata

import co.slandow.caboose.model.metadata.WorkflowDef

interface WorkflowDefService {

    WorkflowDef getWorkflowDef(String workflowDefName)

    List<WorkflowDef> getWorkflowDefs()

    List<WorkflowDef> getWorkflowDefs(int page, int size)

    void saveWorkflowDef(WorkflowDef workflowDef)

}
package co.slandow.caboose.model.metadata

class WorkflowDefTask {

    String name
    String taskReferenceName
    TaskType type
    String description
    boolean optional = false
    Map<String, String> inputParameters

}

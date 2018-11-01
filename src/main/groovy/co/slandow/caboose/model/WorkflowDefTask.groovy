package co.slandow.caboose.model

class WorkflowDefTask {

    String name
    String taskReferenceName
    TaskType type
    String description
    boolean optional = false
    Map<String, String> inputParameters

}

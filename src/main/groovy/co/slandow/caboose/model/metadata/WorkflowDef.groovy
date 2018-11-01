package co.slandow.caboose.model.metadata

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class WorkflowDef {

    @Id
    @JsonIgnore
    String id

    @NotEmpty
    String name

    String description

    @Positive
    int version

    @NotNull
    @NotEmpty
    List<WorkflowDefTask> tasks

    Map<String, String> outputParameters

}

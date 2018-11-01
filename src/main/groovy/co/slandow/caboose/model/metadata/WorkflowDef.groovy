package co.slandow.caboose.model.metadata

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Document
@CompoundIndexes([@CompoundIndex(name="name_version", def = "{'name': 1, 'version': -1}", unique = true)])
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

    List<String> inputParameters

    Map<String, String> outputParameters

}

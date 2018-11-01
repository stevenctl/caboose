package co.slandow.caboose.model.metadata

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

@Document
class TaskDef {

    @Id
    @JsonIgnore
    String id

    @NotNull
    @NotEmpty
    @Indexed
    String name

    @PositiveOrZero
    int retryCount

    RetryLogic retryLogic

    @PositiveOrZero
    int retryDelaySeconds

    @Positive
    int timeoutSeconds

    TimeoutPolicy timeoutPolicy

    List<String> inputKeys

    List<String> outputKeys


}

package co.slandow.caboose.model.metadata

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

class TaskDef {

    @Id
    @JsonIgnore
    String id

    @NotNull
    @NotEmpty
    String name

    @PositiveOrZero
    int retryCount

    RetryLogic retryLogic

    @PositiveOrZero
    int retryDelaySeconds

    @Positive
    int timeoutSeconds

    TimeoutPolicy timeoutPolicy

    List<String> inputKeys = []

    List<String> outputKeys = []


}

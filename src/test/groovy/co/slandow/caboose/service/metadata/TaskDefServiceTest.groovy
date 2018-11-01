package co.slandow.caboose.service.metadata

import co.slandow.caboose.model.metadata.TaskDef
import co.slandow.caboose.service.metadata.impl.TaskDefServiceImpl
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.data.mongodb.core.MongoOperations

import static co.slandow.caboose.model.metadata.RetryLogic.FIXED
import static co.slandow.caboose.model.metadata.TimeoutPolicy.RETRY
import static co.slandow.caboose.util.MongoUtil.buildPagedQuery
import static co.slandow.caboose.util.MongoUtil.buildQuery
import static junit.framework.TestCase.*
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.internal.verification.VerificationModeFactory.times

class TaskDefServiceTest {

    @Mock
    MongoOperations repo

    @InjectMocks
    TaskDefService service = new TaskDefServiceImpl()

    @Before
    void setupMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void getTaskDefByName() {
        // Given
        final TASK_NAME = "my_task"
        when(repo.findOne(buildQuery([name: TASK_NAME]), TaskDef)).thenReturn(new TaskDef(name: TASK_NAME))

        // When
        final taskDef = service.getTaskDef(TASK_NAME)

        // Then
        assertNotNull(taskDef)
        assertEquals(TASK_NAME, taskDef.name)
    }

    @Test
    void getTaskDefByNameReturnsNullIfTaskNotFound() {
        //When
        final t = service.getTaskDef("non_existent")

        // Then
        assertNull(t)
    }


    @Test
    void getTaskDefs() {
        // Given
        final TASKS = (0..4).collect({ new TaskDef(name: "my_task_${it}") }) as List<TaskDef>

        when(repo.findAll(TaskDef)).thenReturn(TASKS)

        // When
        final tasks = service.getTaskDefs()

        // Then
        assertEquals(TASKS.size(), tasks.size())
    }

    @Test
    void getPaginatedTaskDefs() {
        // Given
        final PAGE_SIZE = 10
        final TASK_NAME = "my_workflow"
        final TASKS = (0..PAGE_SIZE - 1).collect({ new TaskDef(name: "${TASK_NAME}_$it") }) as List<TaskDef>

        when(repo.find(buildPagedQuery(0, PAGE_SIZE), TaskDef)).thenReturn(TASKS)

        // When
        final tasks = service.getTaskDefs(0, 10)

        // Then
        assertEquals(TASKS, tasks)
    }

    @Test
    void saveTaskDef() {
        // Given
        final TASK_DEF = new TaskDef(
                name: "my_workflow",
                retryCount: 1,
                retryLogic: FIXED,
                retryDelaySeconds: 10,
                timeoutSeconds: 90,
                timeoutPolicy: RETRY
        )

        // When
        service.saveTaskDef(TASK_DEF)

        // Then
        verify(repo, times(1)).findAndReplace(buildQuery([name: "my_workflow"]), TASK_DEF)
    }

    @Test
    void saveTaskDefFailsWhenPropertiesAreMissing() {
        ["name", "retryCount", "retryDelaySeconds", "timeoutSeconds"].forEach({

            // Given
            final taskDef = new TaskDef(
                    name: "my_workflow",
                    retryCount: 1,
                    retryLogic: FIXED,
                    retryDelaySeconds: 10,
                    timeoutSeconds: 90,
                    timeoutPolicy: RETRY
            )

            taskDef.setProperty(it, TaskDef.getDeclaredField(it).type == int ? -1 : null)

            try {
                // When
                service.saveTaskDef(taskDef)
                fail()
            } catch (IllegalArgumentException ignore) {
                // Then exception is thrown
            }
        })


    }


}
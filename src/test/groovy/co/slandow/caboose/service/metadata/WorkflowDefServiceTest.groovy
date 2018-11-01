package co.slandow.caboose.service.metadata

import co.slandow.caboose.model.TaskType
import co.slandow.caboose.model.WorkflowDef
import co.slandow.caboose.model.WorkflowDefTask
import co.slandow.caboose.repo.WorkflowDefRepo
import co.slandow.caboose.service.metadata.impl.WorkflowDefServiceImpl
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import static junit.framework.TestCase.*
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.internal.verification.VerificationModeFactory.times

class WorkflowDefServiceTest {

    @Mock
    WorkflowDefRepo workflowDefRepo

    @InjectMocks
    WorkflowDefService service = new WorkflowDefServiceImpl()

    @Before
    void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getWorkflowDefByNameReturnsLatestVersion() {
        // Given
        final WF_NAME = "my_workflow"
        when(workflowDefRepo.findByName(WF_NAME))
                .thenReturn((0..2).collect({new WorkflowDef(name: WF_NAME, version: it)}) as List<WorkflowDef>)
        // When
        final wf = service.getWorkflowDef(WF_NAME)

        // Then
        assertNotNull(wf)
        assertEquals(WF_NAME, wf.name)
        assertEquals(2, wf.version)
    }

    @Test
    void getWorkflowDefByNameReturnsNullIfWorkflowNotFound() {
        //When
        final wf = service.getWorkflowDef("non_existent")

        // Then
        assertNull(wf)
    }


    @Test
    void getWorkflowDefsReturnsDefsInDescendingVersionOrder() {
        // Given
        final WF_NAME = "my_workflow"
        final WORKFLOWS = (0..4).collect({ new WorkflowDef(name: "${WF_NAME}_$it", version: it)}) as List<WorkflowDef>

        when(workflowDefRepo.findAll()).thenReturn(WORKFLOWS)

        // When
        final wfs = service.getWorkflowDefs()

        // Then
        assertEquals(WORKFLOWS.size(), wfs.size())
        assertEquals(4, wfs[0].version)
        assertEquals(0, wfs[4].version)
    }

    @Test
    void getPaginatedWorkflowDefs() {
        // Given
        final PAGE_SIZE = 10
        final WF_NAME = "my_workflow"
        final WORKFLOWS = (0..PAGE_SIZE - 1).collect({ new WorkflowDef(name: "${WF_NAME}_$it") }) as List<WorkflowDef>

        when(workflowDefRepo.findAll(new PageRequest(0, PAGE_SIZE))).thenReturn(new PageImpl<WorkflowDef>(WORKFLOWS))

        // When
        final wfs = service.getWorkflowDefs(0, 10)

        // Then
        assertEquals(WORKFLOWS, wfs)
    }

    @Test
    void saveWorkflowDef() {
        // Given
        final WF_DEF = new WorkflowDef(
                name: "my_workflow",
                version: 1,
                tasks: [new WorkflowDefTask(
                        name: "my_task",
                        taskReferenceName: "my_task_in_wf",
                        type: TaskType.SIMPLE)
                ]
        )

        // When
        service.saveWorkflowDef(WF_DEF)

        // Then
        verify(workflowDefRepo, times(1)).save(WF_DEF)
    }

    @Test(expected = IllegalArgumentException)
    void saveWorkflowDefFailsWhenNameIsMissing() {
        // Given
        final WF_DEF = new WorkflowDef(
                version: 1,
                tasks: [new WorkflowDefTask(
                        name: "my_task",
                        taskReferenceName: "my_task_in_wf",
                        type: TaskType.SIMPLE)
                ]
        )

        // When
        service.saveWorkflowDef(WF_DEF)

        // Then exception is thrown
    }

    @Test(expected = IllegalArgumentException)
    void saveWorkflowDefFailsWhenVersionIsMissing() {
        // Given
        final WF_DEF = new WorkflowDef(
                name: "my_workflow",
                tasks: [new WorkflowDefTask(
                        name: "my_task",
                        taskReferenceName: "my_task_in_wf",
                        type: TaskType.SIMPLE)
                ]
        )

        // When
        service.saveWorkflowDef(WF_DEF)

        // Then exception is thrown
    }


    @Test(expected = IllegalArgumentException)
    void saveWorkflowDefFailsWhenTasksAreMissing() {
        // Given
        final WF_DEF = new WorkflowDef(
                name: "my_workflow",
                version: 1,
        )

        // When
        service.saveWorkflowDef(WF_DEF)

        // Then exception is thrown
    }

    @Test(expected = IllegalArgumentException)
    void saveWorkflowDefFailsTasksAreInvalid() {
        // Given
        final WF_DEF = new WorkflowDef(
                name: "my_workflow",
                tasks: [new WorkflowDefTask()]
        )

        // When
        service.saveWorkflowDef(WF_DEF)

        // Then exception is thrown
    }

}
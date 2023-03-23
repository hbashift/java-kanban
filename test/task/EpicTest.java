package task;

import static org.junit.jupiter.api.Assertions.*;
import static task.TaskStatus.*;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class EpicTest {

    private Epic epic;
    private TaskManager manager;
    private final String error = "Epic имеет статус";

    @BeforeEach
    public void beforeEach() {
        epic =  new Epic("epicName", "epicDescription", NEW);
        manager = Managers.getInMemoryTask();
    }

    @Test
    public void shouldHasTaskStatusNew() {
        final int epicId = manager.addNewEpic(epic);

        // First case, when epic has no subtasks
        assertEquals(NEW, epic.getStatus(), error + " вместо NEW, когда список подзадач Epic'а пустой");

        Subtask subtask1 = new Subtask("subtaskName", "subtaskDescription", NEW, epicId);
        Subtask subtask2 = new Subtask("subtask1Name", "subtask1Description", NEW, epicId);
        Subtask subtask3 = new Subtask("subtask2Name", "subtask2Description", NEW, epicId);

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);

        // Second case, when epic has subtasks with TaskStatus.NEW
        assertEquals(NEW, epic.getStatus(), error  + "вместо NEW, когда все подзадачи имеет статус NEW");
    }

    @Test
    public void shouldHasTaskStatusInProgress() {
        final int epicId = manager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("subtaskName", "subtaskDescription", NEW, epicId);
        Subtask subtask2 = new Subtask("subtask1Name", "subtask1Description", IN_PROGRESS, epicId);
        Subtask subtask3 = new Subtask("subtask2Name", "subtask2Description", NEW, epicId);

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);

        // First case when one subtask has TaskStatus.IN_PROGRESS, while others have TaskStatus.NEW
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач IN_PROGRESS, остальные NEW"
        );

        subtask1.setStatus(DONE);
        subtask3.setStatus(DONE);

        // Second case when one subtask has TaskStatus.IN_PROGRESS, while others have TaskStatus.DONE
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач IN_PROGRESS, остальные DONE"
        );

        subtask1.setStatus(NEW);
        subtask2.setStatus(NEW);

        // Third case when one subtask has TaskStatus.DONE, while others have TaskStatus.NEW
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач DONE, остальные NEW"
        );

        subtask1.setStatus(IN_PROGRESS);
        subtask2.setStatus(IN_PROGRESS);
        subtask3.setStatus(IN_PROGRESS);

        // Fourth case when all subtasks have TaskStatus.IN_PROGRESS
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда все подзадачи IN_PROGRESS"
        );
    }

    @Test
    public void shouldHasTaskStatusDone() {
        final int epicId = manager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("subtaskName", "subtaskDescription", DONE, epicId);
        Subtask subtask2 = new Subtask("subtask1Name", "subtask1Description", DONE, epicId);
        Subtask subtask3 = new Subtask("subtask2Name", "subtask2Description", DONE, epicId);

        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);

        // If all subtasks have TaskStatus.DONE, then their Epic must have TaskStatus.DONE
        assertEquals(DONE, epic.getStatus(), error + epic.getStatus().toString() + "вместо DONE");
    }

    @Test
    public void addSubtaskIdToAListOfSubtasks() {
        final int epicId = manager.addNewEpic(epic);

        Subtask subtask = new Subtask("subtaskName", "subtaskDescription", DONE, epicId);

        final int subtaskId = manager.addNewSubtask(subtask);

        assertTrue(epic.getSubtasks().contains(subtaskId)
                , "ID Subtask'а не был добавлен в список подзадач Epic'а");
    }

    @Test
    public void setSubtasksIdsToAListOfSubtasks() {
        final int epicId = manager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("subtaskName", "subtaskDescription", DONE, epicId);
        Subtask subtask2 = new Subtask("subtask1Name", "subtask1Description", DONE, epicId);
        Subtask subtask3 = new Subtask("subtask2Name", "subtask2Description", DONE, epicId);

        final int subtask1Id = manager.addNewSubtask(subtask1)
                , subtask2Id = manager.addNewSubtask(subtask2)
                , subtask3Id = manager.addNewSubtask(subtask3);

        List<Integer> subtasksIds = List.of(subtask1Id, subtask2Id, subtask3Id);

        epic.setSubtaskIds(subtasksIds);

        assertEquals(subtasksIds, epic.getSubtasks(), "Список подзадач Epic не был установлен корректно");
    }
}
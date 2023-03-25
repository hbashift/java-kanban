package task;

import static org.junit.jupiter.api.Assertions.*;
import static task.TaskStatus.*;

import manager.TaskManager;
import manager.memory.InMemoryTasksManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class EpicTest {

    private Epic epic;
    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        epic =  new Epic("epicName", "epicDescription", NEW);
        taskManager = new InMemoryTasksManager();
    }

    @Test
    public void addSubtaskToAnEpic() {
        final int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("subtaskName", "subtaskDescription", DONE, epicId);

        final int subtaskId = taskManager.addNewSubtask(subtask);

        assertTrue(epic.getSubtasks().contains(subtaskId)
                , "ID Subtask'а не был добавлен в список подзадач Epic'а");
    }

    @Test
    public void setSubtasksToAnEpic() {
        final int epicId = taskManager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("subtask1Name", "subtask1Description", TaskStatus.DONE, epicId);
        Subtask subtask2 = new Subtask("subtask2Name", "subtask2Description", TaskStatus.DONE, epicId);
        Subtask subtask3 = new Subtask("subtask3Name", "subtask3Description", TaskStatus.DONE, epicId);

        final int subtask1Id = taskManager.addNewSubtask(subtask1), subtask2Id = taskManager.addNewSubtask(subtask2), subtask3Id = taskManager.addNewSubtask(subtask3);

        List<Integer> subtasksIds = List.of(subtask1Id, subtask2Id, subtask3Id);

        epic.setSubtaskIds(subtasksIds);

        assertEquals(subtasksIds, epic.getSubtasks(), "Список подзадач Epic не был установлен корректно");
    }
}
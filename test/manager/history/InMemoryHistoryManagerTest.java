package manager.history;

import static org.junit.jupiter.api.Assertions.*;
import static task.TaskStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public class InMemoryHistoryManagerTest {

    private HistoryManager manager;
    private Task task;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryHistoryManager();
        task = new Task("taskName", "taskDesc", NEW);
    }

    @Test
    public void shouldNotAddDuplicates() {
        manager.add(task);
        assertEquals(1, manager.getHistory().size());
    }

    @Test
    public void shouldReturnEmptyListIfHistoryIsEmpty() {
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void remove() {
        Epic epic = new Epic("epicName", "epicDesc", NEW);
        epic.setId(1);
        Subtask subtask1 = new Subtask("subtask1Name", "subtask1Description", DONE, 1);
        Subtask subtask2 = new Subtask("subtask2Name", "subtask2Description",  DONE, 1);
        Subtask subtask3 = new Subtask("subtask3Name", "subtask3Description", DONE, 1);
        subtask1.setId(2);
        subtask2.setId(3);
        subtask3.setId(4);
        task.setId(5);

        manager.add(epic);
        manager.add(subtask1);
        manager.add(subtask2);
        manager.add(subtask3);
        manager.add(task);
        // Deleting from the beginning
        manager.remove(1);
        assertEquals(List.of(2, 3, 4, 5), manager.getHistory());
        // Deleting the last el
        manager.remove(5);
        assertEquals(List.of(2, 3, 4), manager.getHistory());
        // Deleting from the middle
        manager.remove(3);
        assertEquals(List.of(2, 4), manager.getHistory());
        // Deleting all
        manager.removeAll();
        assertTrue(manager.getHistory().isEmpty());
    }
}

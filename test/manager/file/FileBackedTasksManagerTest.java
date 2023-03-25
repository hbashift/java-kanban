package manager.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.*;
import static task.TaskStatus.*;

import manager.TaskManagerTest;
import org.junit.jupiter.api.*;
import task.Subtask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private static Path path;

    @BeforeAll
    public static void fileSetUp() {
        path = Paths.get("test resources/task.csv");
    }

    @BeforeEach
    public void setUp() {
        taskManager = new FileBackedTasksManager(path.toFile());
    }

    @Test
    public void loadFromFile() {
        String error = "Wrong parsing";
        FileBackedTasksManager backedTasksManager = FileBackedTasksManager.loadFromFile(path.toFile());
        // Checking while there is no tasks exist
        assertEquals(taskManager.getTasks(), backedTasksManager.getTasks(), error);
        assertEquals(taskManager.getSubtasks(), backedTasksManager.getSubtasks(), error);
        assertEquals(taskManager.getEpics(), backedTasksManager.getEpics(), error);
        assertEquals(taskManager.getHistory(), backedTasksManager.getHistory(), error);

        int epicId = taskManager.addNewEpic(epic);
        int taskId = taskManager.addNewTask(task);

        backedTasksManager = FileBackedTasksManager.loadFromFile(path.toFile());
        // Checking while Epic has no Subtasks
        assertEquals(taskManager.getTasks(), backedTasksManager.getTasks(), error);
        assertEquals(taskManager.getSubtasks(), backedTasksManager.getSubtasks(), error);
        assertEquals(taskManager.getEpics(), backedTasksManager.getEpics(), error);
        assertEquals(taskManager.getHistory(), backedTasksManager.getHistory(), error);

        for (Subtask task : new ArrayList<>(createSubtasks(DONE, DONE, DONE, epicId))) {
            taskManager.addNewSubtask(task);
        }

        backedTasksManager = FileBackedTasksManager.loadFromFile(path.toFile());
        // Checking while history is empty
        assertEquals(taskManager.getTasks(), backedTasksManager.getTasks(), error + ": empty history");
        assertEquals(taskManager.getSubtasks(), backedTasksManager.getSubtasks(), error + ": empty history");
        assertEquals(taskManager.getEpics(), backedTasksManager.getEpics(), error + ": empty history");
        assertEquals(taskManager.getHistory(), backedTasksManager.getHistory(), error + ": empty history");

        taskManager.getTask(taskId);
        taskManager.getEpic(epicId);
        taskManager.getEpic(epicId);

        backedTasksManager = FileBackedTasksManager.loadFromFile(path.toFile());
        // Checking in common situation
        assertEquals(taskManager.getTasks(), backedTasksManager.getTasks(), error);
        assertEquals(taskManager.getSubtasks(), backedTasksManager.getSubtasks(), error);
        assertEquals(taskManager.getEpics(), backedTasksManager.getEpics(), error);
        assertEquals(taskManager.getHistory(), backedTasksManager.getHistory(), error);
    }

    @AfterAll
    public static void afterEach() throws IOException {
        Files.move(path, Paths.get("test resources", "test_data", path.getFileName().toString()),
                REPLACE_EXISTING);
    }
}

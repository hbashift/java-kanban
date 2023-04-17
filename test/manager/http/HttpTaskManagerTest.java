package manager.http;

import KV.server.KVServer;
import manager.TaskManagerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Subtask;
import task.TaskStatus;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private final static int PORT = 8078;
    private final static String API_TOKEN = "DEBUG";

    @BeforeAll
    public static void setUp() {
        KVServer kvServer;

        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        kvServer.start();
    }

    @BeforeEach
    public void setHttpTaskManager() {
        taskManager = new HttpTaskManager(URI.create("http://localhost:" + PORT + "/"));
    }

    @Test
    public void testName() {
        int taskId = taskManager.addNewTask(task);
        int epicId = taskManager.addNewEpic(epic);
        subtask = new Subtask("subtaskName", "subtaskDesc", TaskStatus.NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        HttpTaskManager testHttpTaskManager = new HttpTaskManager(URI.create("http://localhost:" + PORT + "/"));

        assertEquals(taskManager.getTask(taskId), testHttpTaskManager.getTask(taskId));
        assertEquals(taskManager.getSubtask(subtaskId), testHttpTaskManager.getSubtask(subtaskId));
        assertEquals(taskManager.getEpic(epicId), testHttpTaskManager.getEpic(epicId));
    }
}
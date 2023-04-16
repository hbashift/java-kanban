package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.file.FileBackedTasksManager;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import manager.http.HttpTaskManager;
import manager.memory.InMemoryTasksManager;

import java.io.File;
import java.net.URI;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new HttpTaskManager(URI.create("http://localhost:8078/"));
    }

    public static TaskManager getInMemoryTask() {
        return new InMemoryTasksManager();
    }

    public static HistoryManager getHistoryManagerDefault() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getFileManager() {
        return new FileBackedTasksManager(new File("resources/tasks.csv"));
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}
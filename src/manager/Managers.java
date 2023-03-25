package manager;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import manager.file.FileBackedTasksManager;
import manager.memory.InMemoryTasksManager;

import java.io.File;

public class Managers {

    private Managers() {}

    public static TaskManager getDefault(File file) {
        return new FileBackedTasksManager(file);
    }

    public static TaskManager getInMemoryTask() {
        return new InMemoryTasksManager();
    }

    public static HistoryManager getHistoryManagerDefault() {
        return new InMemoryHistoryManager();
    }
}
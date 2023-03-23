package manager;

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
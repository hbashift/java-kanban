package manager;

import java.io.File;

public class Managers {

    private Managers() {}

    public static TaskManager getDefault(File file) {
        return new FileBackedManager(file);
    }

    public static HistoryManager getHistoryManagerDefault() {
        return new InMemoryHistoryManager();
    }
}
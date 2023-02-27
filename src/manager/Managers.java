package manager;

import exceptions.ManagerSaveException;

import java.io.IOException;
import java.nio.file.Path;

public class Managers {

    private Managers() {}

    public static TaskManager getDefault() {
        FileBackedManager fileBackedManager = new FileBackedManager(
                Path.of("resources", "tasks.csv").toFile()
        );

        try {
            fileBackedManager.loadFromFile();
        } catch (IOException | ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return fileBackedManager;
    }

    public static HistoryManager getHistoryManagerDefault() {
        return new InMemoryHistoryManager();
    }
}
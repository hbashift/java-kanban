package manager.http;

import manager.TaskManager;
import manager.file.FileBackedTasksManager;

import java.io.File;

public class HttpTaskManager extends FileBackedTasksManager implements TaskManager {

    public HttpTaskManager(File file) {
        super(file);
    }
}

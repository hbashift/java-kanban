package manager.http;

import KV.client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.file.FileBackedTasksManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.net.URI;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient taskClient;
    private final Gson gson;

    public HttpTaskManager(URI url) {
        super();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        taskClient = new KVTaskClient(url);
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);

        if (id == -1)
            return -1;

        taskClient.put(task.getId().toString(), gson.toJson(task));

        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);

        if (id == -1)
            return -1;

        taskClient.put(subtask.getId().toString(), gson.toJson(subtask));

        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);

        if (id == -1)
            return -1;

        taskClient.put(epic.getId().toString(), gson.toJson(epic));

        return id;
    }

    @Override
    public Task getTask(int id) {
        return gson.fromJson(taskClient.load(String.valueOf(id)), Task.class);
    }

    @Override
    public Subtask getSubtask(int id) {
        return gson.fromJson(taskClient.load(String.valueOf(id)), Subtask.class);
    }

    @Override
    public Epic getEpic(int id) {
        return gson.fromJson(taskClient.load(String.valueOf(id)), Epic.class);
    }

    @Override
    public void updateTask(Task task) {
        if (task.getId() == null)
            return;

        taskClient.put(task.getId().toString(), gson.toJson(task));
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask.getId() == null) {
            return;
        }

        taskClient.put(subtask.getId().toString(), gson.toJson(subtask));
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic.getId() == null)
            return;

        taskClient.put(epic.getId().toString(), gson.toJson(epic));
    }
}

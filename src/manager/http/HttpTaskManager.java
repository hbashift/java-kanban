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

    private KVTaskClient taskClient;
    private Gson gson;

    public HttpTaskManager(URI url) {
        super();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        taskClient = new KVTaskClient(url);
    }

    @Override
    public int addNewTask(Task task) {
        task.setId(++generatorId);
        taskClient.put(task.getId().toString(), gson.toJson(task));

        return generatorId;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        subtask.setId(++generatorId);
        taskClient.put(subtask.getId().toString(), gson.toJson(subtask));

        return generatorId;
    }

    @Override
    public int addNewEpic(Epic epic) {
        epic.setId(++generatorId);
        taskClient.put(epic.getId().toString(), gson.toJson(epic));

        return generatorId;
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
        taskClient.put(task.getId().toString(), gson.toJson(task));
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        taskClient.put(subtask.getId().toString(), gson.toJson(subtask));
    }

    @Override
    public void updateEpic(Epic epic) {
        taskClient.put(epic.getId().toString(), gson.toJson(epic));
    }
}

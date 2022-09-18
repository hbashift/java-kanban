package manager;

import task.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int generatorId;
    // Constructor
    public Manager() {
        generatorId = 0;
    }
    // Adding new task.Task/task.Subtask/task.Epic to a manager
    public int addNewTask(Task task) {
        task.setId(++generatorId);
        tasks.put(task.getId(), task);

        return generatorId;
    }

    public int addNewSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId()))
            return -1;

        subtask.setId(++generatorId);
        subtasks.put(subtask.getId(), subtask);

        epics.get(subtask.getEpicId()).addSubtaskId(subtask);
        changeEpicStatus(subtask);

        return generatorId;
    }

    public int addNewEpic(Epic epic) {
        epic.setId(++generatorId);
        epics.put(epic.getId(), epic);

        return generatorId;
    }
    // getters for task.Task/task.Subtask/task.Epic HashMaps
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
    // getter for subtaskIds ArrayList from concrete task.Epic
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (int id : epic.getSubtasks()) {
            subtasks.add(this.subtasks.get(id));
        }

        return subtasks;
    }
    // getters for task.Task/task.Subtask/task.Epic by id
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }
    // update methods for task.Task/task.Subtask/task.Epic
    public void updateTask(Task task) {
        tasks.replace(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.replace(subtask.getId(), subtask);

        changeEpicStatus(subtask);
    }

    public void updateEpic(Epic epic) {
        epics.replace(epic.getId(), epic);
    }
    // status checker for task.Epic
    private Status checkEpicStatus(Epic epic) {
        Status checker = Status.NEW;
        ArrayList<Status> statusArrayList = new ArrayList<>();

        if (epic.getSubtasks().size() == 0) {
            return checker;
        }

        for (int subtaskId : epic.getSubtasks()) {
            statusArrayList.add(subtasks.get(subtaskId).getStatus());
        }

        for (Status status : statusArrayList) {
            if (!status.equals(statusArrayList.get(0))) {
                checker = Status.IN_PROGRESS;
                return checker;
            } else {
                checker = statusArrayList.get(0);
            }
        }
        return checker;
    }
    // status changer
    private void changeEpicStatus(Subtask subtask){
        Epic epic = epics.get(subtask.getEpicId());
        Status status = checkEpicStatus(epics.get(subtask.getEpicId()));

        switch (status) {
            case NEW:
                epic.setStatus(Status.NEW);
                break;
            case IN_PROGRESS:
                epic.setStatus(Status.IN_PROGRESS);
                break;
            case DONE:
                epic.setStatus(Status.DONE);
                break;
        }
    }
    // delete methods for task.Task/task.Subtask/task.Epic (all and by id)
    private void deleteAll() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }

        subtasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getEpicId()).getSubtasks().remove(subtask.getId());
        changeEpicStatus(subtask);
        subtasks.remove(id);
    }

    public void deleteEpicById(int id) {
        ArrayList<Integer> subtasksIdList = epics.get(id).getSubtasks();

        for (int i : subtasksIdList) {
            subtasks.remove(i);
        }

        epics.remove(id);
    }
}

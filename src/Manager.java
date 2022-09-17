import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> TaskHashMap;
    HashMap<Integer, Subtask> SubtaskHashMap;
    HashMap<Integer, Epic> EpicHashMap;
    static int id;
    // Constructor
    public Manager() {
        TaskHashMap = new HashMap<>();
        SubtaskHashMap = new HashMap<>();
        EpicHashMap = new HashMap<>();
        id = 0;
    }
    // Adding new Task/Subtask/Epic to a manager
    public int addNewTask(Task task) {
        task.setId(++id);
        TaskHashMap.put(task.getId(), task);

        return id;
    }

    public int addNewSubtask(Subtask subtask) {
        subtask.setId(++id);
        SubtaskHashMap.put(subtask.getId(), subtask);

        EpicHashMap.get(subtask.getEpicId()).addSubtaskId(subtask);
        changeEpicStatus(subtask);

        return id;
    }

    public int addNewEpic(Epic epic) {
        epic.setId(++id);
        EpicHashMap.put(epic.getId(), epic);

        return id;
    }
    // getters for Task/Subtask/Epic HashMaps
    public HashMap<Integer, Task> getTasks() {
        return TaskHashMap;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return SubtaskHashMap;
    }

    public HashMap<Integer, Epic> getEpics() {
        return EpicHashMap;
    }
    // getter for subtaskIds ArrayList from concrete Epic
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (int id : epic.getSubtasks()) {
            subtasks.add(SubtaskHashMap.get(id));
        }

        return subtasks;
    }
    // getters for Task/Subtask/Epic by id
    public Task getTaskById(int id) {
        return TaskHashMap.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return SubtaskHashMap.get(id);
    }

    public Epic getEpicById(int id) {
        return EpicHashMap.get(id);
    }
    // update methods for Task/Subtask/Epic
    public void updateTask(Task task) {
        TaskHashMap.replace(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        SubtaskHashMap.replace(subtask.getId(), subtask);

        changeEpicStatus(subtask);
    }

    public void updateEpic(Epic epic) {
        EpicHashMap.replace(epic.getId(), epic);
    }
    // status checker for Epic
    public Status checkEpicStatus(Epic epic) {
        Status checker = Status.NEW;
        ArrayList<Status> statusArrayList = new ArrayList<>();

        if (epic.getSubtasks().size() == 0) {
            return checker;
        }

        for (int subtaskId : epic.getSubtasks()) {
            statusArrayList.add(SubtaskHashMap.get(subtaskId).getStatus());
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
        Epic epic = EpicHashMap.get(subtask.getEpicId());
        Status status = checkEpicStatus(EpicHashMap.get(subtask.getEpicId()));

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
    // delete methods for Task/Subtask/Epic (all and by id)
    private void deleteAll() {
        TaskHashMap.clear();
        SubtaskHashMap.clear();
        EpicHashMap.clear();
    }

    public void deleteAllTasks() {
        TaskHashMap.clear();
    }

    public void deleteAllSubtasks() {
        for (Epic epic : EpicHashMap.values()) {
            epic.getSubtasks().clear();
        }

        SubtaskHashMap.clear();
    }

    public void deleteAllEpics() {
        EpicHashMap.clear();
        SubtaskHashMap.clear();
    }

    public void deleteTaskById(int id) {
        TaskHashMap.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = SubtaskHashMap.get(id);
        EpicHashMap.get(subtask.getEpicId()).getSubtasks().remove(subtask.getId());
        changeEpicStatus(subtask);
        SubtaskHashMap.remove(id);
    }

    public void deleteEpicById(int id) {
        ArrayList<Integer> subtasksIdList = EpicHashMap.get(id).getSubtasks();

        for (int i : subtasksIdList) {
            SubtaskHashMap.remove(i);
        }

        EpicHashMap.remove(id);
    }
}

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> TaskHashMap;
    HashMap<Integer, Subtask> SubtaskHashMap;
    HashMap<Integer, Epic> EpicHashMap;
    static int id;

    public Manager() {
        TaskHashMap = new HashMap<>();
        SubtaskHashMap = new HashMap<>();
        EpicHashMap = new HashMap<>();
        id = 1;
    }

    public void newTask(Task task) {
        task.setId(id++);
        TaskHashMap.put(task.getId(), task);
    }

    public void newSubtask(Subtask subtask) {
        subtask.setId(id++);
        SubtaskHashMap.put(subtask.getId(), subtask);
        EpicHashMap.get(subtask.getEpicId()).getSubtasks().add(subtask.getId());
    }

    public void newEpic(Epic epic) {
        epic.setId(id++);
        EpicHashMap.put(epic.getId(), epic);
    }

    public Task getTaskById(int id) {
        return TaskHashMap.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return SubtaskHashMap.get(id);
    }

    public Epic getEpicById(int id) {
        return EpicHashMap.get(id);
    }

    public void updateTask(Task task) {

        // TODO узнать про то как статусы задаются
        if (task.getStatus() == Status.NEW) {
            task.setStatus(Status.IN_PROGRESS);
        }

        TaskHashMap.replace(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask.getStatus() == Status.NEW) {
            subtask.setStatus(Status.IN_PROGRESS);
        }

        Epic epic = EpicHashMap.get(subtask.getEpicId());
        Status status = checkEpicStatus(EpicHashMap.get(subtask.getEpicId()));

        switch (status) {
            case NEW:
                epic.setStatus(Status.NEW);
            case IN_PROGRESS:
                epic.setStatus(Status.IN_PROGRESS);
            case DONE:
                epic.setStatus(Status.DONE);
        }

        SubtaskHashMap.replace(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        EpicHashMap.replace(epic.getId(), epic);
    }

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
            } else {
                checker = statusArrayList.get(0);
            }
        }
        return checker;
    }

    private void deleteAll() {
        TaskHashMap.clear();
        SubtaskHashMap.clear();
        EpicHashMap.clear();
    }

    public void deleteAllTasks() {
        TaskHashMap.clear();
    }

    public void deleteAllSubtasks() {
        SubtaskHashMap.clear();
    }

    public void deleteAllEpics() {
        EpicHashMap.clear();
    }

    public void deleteTaskById(int id) {
        TaskHashMap.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = SubtaskHashMap.get(id);
        EpicHashMap.get(subtask.getEpicId()).getSubtasks().remove(id);

        SubtaskHashMap.remove(id);
    }

    public void deleteEpicById(int id) {
        ArrayList<Integer> subtasksIdList = EpicHashMap.get(id).getSubtasks();

        for (int i : subtasksIdList) {
            SubtaskHashMap.remove(i);
        }

        EpicHashMap.remove(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (int id : epic.getSubtasks()) {
            subtasks.add(SubtaskHashMap.get(id));
        }

        return subtasks;
    }
}

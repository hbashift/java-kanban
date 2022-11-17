package manager;

import task.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getHistoryManagerDefault();
    private int generatorId;

    // Constructor
    public InMemoryTaskManager() {
        generatorId = 0;
    }

    // Adding new task.Task/task.Subtask/task.Epic to a InMemoryTaskManager
    @Override
    public int addNewTask(Task task) {
        task.setId(++generatorId);
        tasks.put(task.getId(), task);

        return generatorId;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId()))
            return -1;

        subtask.setId(++generatorId);
        subtasks.put(subtask.getId(), subtask);

        epics.get(subtask.getEpicId()).addSubtaskId(subtask);
        changeEpicStatus(subtask);

        return generatorId;
    }

    @Override
    public int addNewEpic(Epic epic) {
        epic.setId(++generatorId);
        epics.put(epic.getId(), epic);

        return generatorId;
    }

    // getters for task.Task/task.Subtask/task.Epic HashMaps
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // getter for subtaskIds ArrayList from concrete task.Epic
    @Override
    public List<Subtask> getEpicSubtasks(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (int id : epic.getSubtasks()) {
            subtasks.add(this.subtasks.get(id));
        }

        return subtasks;
    }

    // getters for task.Task/task.Subtask/task.Epic by id
    @Override
    public Task getTask(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        final Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    // update methods for task.Task/task.Subtask/task.Epic
    @Override
    public void updateTask(Task task) {
        tasks.replace(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.replace(subtask.getId(), subtask);

        changeEpicStatus(subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.replace(epic.getId(), epic);
    }

    // taskStatus checker for task.Epic
    private TaskStatus checkEpicStatus(Epic epic) {
        TaskStatus checker = TaskStatus.NEW;
        ArrayList<TaskStatus> taskStatusArrayList = new ArrayList<>();

        if (epic.getSubtasks().size() == 0) {
            return checker;
        }

        for (int subtaskId : epic.getSubtasks()) {
            taskStatusArrayList.add(subtasks.get(subtaskId).getStatus());
        }

        for (TaskStatus taskStatus : taskStatusArrayList) {
            if (!taskStatus.equals(taskStatusArrayList.get(0))) {
                checker = TaskStatus.IN_PROGRESS;
                return checker;
            } else {
                checker = taskStatusArrayList.get(0);
            }
        }
        return checker;
    }

    // taskStatus changer
    private void changeEpicStatus(Subtask subtask){
        Epic epic = epics.get(subtask.getEpicId());
        TaskStatus taskStatus = checkEpicStatus(epics.get(subtask.getEpicId()));

        switch (taskStatus) {
            case NEW:
                epic.setStatus(TaskStatus.NEW);
                break;
            case IN_PROGRESS:
                epic.setStatus(TaskStatus.IN_PROGRESS);
                break;
            case DONE:
                epic.setStatus(TaskStatus.DONE);
                break;
        }
    }

    // delete methods for task.Task/task.Subtask/task.Epic (all and by id)
    private void deleteAll() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        historyManager.removeAll();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }

        subtasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        if (!subtasks.containsKey(id)) {
            historyManager.remove(id);
            return;
        }

        epics.get(subtask.getEpicId()).getSubtasks().remove(subtask.getId());
        changeEpicStatus(subtask);

        subtasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        ArrayList<Integer> subtasksIdList = epics.get(id).getSubtasks();

        for (int taskId : subtasksIdList) {
            subtasks.remove(taskId);
        }

        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void printAll() {
        List<Task> taskList = new ArrayList<>(tasks.values());
        List<Subtask> subtasksList = new ArrayList<>(subtasks.values());
        List<Epic> epicsList = new ArrayList<>(epics.values());

        System.out.println("Printing all tasks");
        taskList.forEach(System.out::println);
        System.out.println('\n');

        System.out.println("Printing all subtasks");
        subtasksList.forEach(System.out::println);
        System.out.println('\n');

        System.out.println("Printing all epics");
        epicsList.forEach(System.out::println);
    }
}

package manager.memory;

import manager.Managers;
import manager.history.HistoryManager;
import manager.TaskManager;
import task.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryTasksManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getHistoryManagerDefault();
    protected int generatorId;

    // Constructor
    public InMemoryTasksManager() {
        generatorId = 0;
    }

    // Adding new task.Task/task.Subtask/task.Epic to a InMemoryTasksManager
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

    // Adding new new task.Task/task.Subtask/task.Epic to a InMemoryTasksManager with id
    protected void addNewTask(Task task, int id) {
        task.setId(id);
        tasks.put(id, task);
    }
    protected void addNewSubtask(Subtask subtask, int id) {
        if (!epics.containsKey(subtask.getEpicId()))
            return;

        subtask.setId(id);
        subtasks.put(id, subtask);

        epics.get(subtask.getEpicId()).addSubtaskId(subtask);
        changeEpicStatus(subtask);
    }

    protected void addNewEpic(Epic epic, int id) {
        epic.setId(id);
        epics.put(id, epic);
    }

    // getters for task.Task/task.Subtask/task.Epic HashMaps
    @Override
    public List<Task> getTasks() {
        if (!tasks.values().isEmpty())
            return new ArrayList<>(tasks.values());
        else
            return null;

    }

    @Override
    public List<Subtask> getSubtasks() {
        if (!subtasks.values().isEmpty())
            return new ArrayList<>(subtasks.values());
        else
            return null;

    }

    @Override
    public List<Epic> getEpics() {
        if (!epics.values().isEmpty())
            return new ArrayList<>(epics.values());
        else
            return null;

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
        final Task task = tasks.getOrDefault(id, null);

        if (task != null)
            historyManager.add(task);

        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        final Subtask subtask = subtasks.getOrDefault(id, null);

        if (subtask != null)
            historyManager.add(subtask);

        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = epics.getOrDefault(id, null);

        if (epic != null)
            historyManager.add(epic);

        return epic;
    }

    // update methods for task.Task/task.Subtask/task.Epic
    @Override
    public boolean updateTask(Task task)  {
        boolean check = false;

        if (tasks.containsKey(task.getId())) {
            tasks.replace(task.getId(), task);
            check = true;
        }

        return check;
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        boolean check = false;

        if (subtasks.containsKey(subtask.getId())) {
            subtasks.replace(subtask.getId(), subtask);
            changeEpicStatus(subtask);
            check = true;
        }

        return check;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        boolean check = false;

        if (epics.containsKey(epic.getId())) {
            epics.replace(epic.getId(), epic);
            check = true;
        }

        return check;
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
        if (!tasks.values().isEmpty()) {
            for (Task task : tasks.values()) {
                historyManager.remove(task.getId());
            }
        }

        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        if (!epics.values().isEmpty() && !subtasks.values().isEmpty()){
            for (Epic epic : epics.values()) {
                epic.getSubtasks().clear();
            }

            for (Subtask subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
            }
        }

        subtasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        if (!subtasks.values().isEmpty() && !epics.values().isEmpty()) {
            for (Subtask subtask : subtasks.values()) {
                historyManager.remove(subtask.getId());
            }

            for (Epic epic : epics.values()) {
                historyManager.remove(epic.getId());
            }
        }

        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task deleteTask(int id) {
        historyManager.remove(id);

        return tasks.remove(id);
    }

    @Override
    public Subtask deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        if (!subtasks.containsKey(id)) {
            historyManager.remove(id);
            return subtask;
        }

        epics.get(subtask.getEpicId()).getSubtasks().remove(subtask.getId());
        changeEpicStatus(subtask);

        subtasks.remove(id);
        historyManager.remove(id);

        return subtask;
    }

    @Override
    public Epic deleteEpic(int id) {
        if (epics.containsKey(id)) {
            List<Integer> subtasksIdList = epics.get(id).getSubtasks();

            for (int subtaskId : subtasksIdList) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
        }

        historyManager.remove(id);
        return epics.remove(id);
    }

    @Override
    public List<Integer> getHistory() {
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
        System.out.println('\n');

        System.out.println("Printing history");
        historyManager.printAll();
    }

    // sets history
    @Override
    public void setHistory(List<Integer> history) {
        for (int id : history) {
            if (tasks.containsKey(id)) {
                historyManager.add(tasks.get(id));
            } else if (subtasks.containsKey(id)) {
                historyManager.add(subtasks.get(id));
            } else if (epics.containsKey(id)) {
                historyManager.add(epics.get(id));
            }
        }
    }
}
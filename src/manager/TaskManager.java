package manager;

import task.*;

import java.util.List;

public interface TaskManager {
    // Adding new task.Task/task.Subtask/task.Epic to a manager
    int addNewTask(Task task);

    int addNewSubtask(Subtask subtask);

    int addNewEpic(Epic epic);

    // getters for task.Task/task.Subtask/task.Epic HashMaps
    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    // getter for subtaskIds ArrayList from concrete task.Epic
    List<Subtask> getEpicSubtasks(Epic epic);

    // getters for task.Task/task.Subtask/task.Epic by id
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    // update methods for task.Task/task.Subtask/task.Epic
    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    // delete methods for task.Task/task.Subtask/task.Epic
    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpic(int id);

    // getter for the task browsing history
    List<Integer> getHistory();

    void printAll();
}

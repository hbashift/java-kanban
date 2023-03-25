package manager;

import task.*;

import java.util.List;

public interface TaskManager {
    // Adding new task.Task/task.Subtask/task.Epic to a manager
    // Should return taskId
    int addNewTask(Task task);

    /* Should return subtaskId, or -1
     * if there is no such Epic with epicId
     * that was used while creating subtask
     * */
    int addNewSubtask(Subtask subtask);

    // Should return epicId
    int addNewEpic(Epic epic);

    // getters for task.Task/task.Subtask/task.Epic HashMaps
    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Epic> getEpics();

    // getter for subtaskIds ArrayList from concrete task.Epic
    List<Subtask> getEpicSubtasks(Epic epic);

    // getters for task.Task/task.Subtask/task.Epic by id
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    // update methods for task.Task/task.Subtask/task.Epic
    // return true, if update is successful, else false
    boolean updateTask(Task task) ;

    boolean updateSubtask(Subtask subtask);

    boolean updateEpic(Epic epic);

    // delete methods for task.Task/task.Subtask/task.Epic
    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    // return task.Task/Subtask/Epic that was deleted
    Task deleteTask(int id);

    Subtask deleteSubtask(int id);

    Epic deleteEpic(int id);

    // getter for the task browsing history
    List<Integer> getHistory();

    void setHistory(List<Integer> history);

    void printAll();
}

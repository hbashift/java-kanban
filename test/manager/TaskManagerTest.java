package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static task.TaskStatus.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    private List<Subtask> subtasks;
    private final String error = "Epic имеет статус ";

    protected List<Subtask> createSubtasks(TaskStatus status1, TaskStatus status2, TaskStatus status3, int epicId) {

        Subtask subtask1 = new Subtask("subtask1Name", "subtask1Description", status1, epicId,
                LocalDateTime.now(), Duration.ofMinutes(100));
        Subtask subtask2 = new Subtask("subtask2Name", "subtask2Description", status2, epicId,
                LocalDateTime.now().plusHours(10), Duration.ofMinutes(10));
        Subtask subtask3 = new Subtask("subtask3Name", "subtask3Description", status3, epicId,
                LocalDateTime.now().minusHours(10), Duration.ofMinutes(10));

        List<Subtask> subtasks = new ArrayList<>();

        subtasks.add(subtask1);
        subtasks.add(subtask2);
        subtasks.add(subtask3);

        return subtasks;
    }

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("epicName", "epicDescription", NEW);
        task = new Task("taskName", "taskDescription", NEW);
    }

    /*
     * Checking for the correctness of the epic status change.
     * Epic should have status:
     *       - if all subtasks are DONE -> DONE,
     *       - if all subtasks are NEW -> NEW,
     *       - else -> IN_PROGRESS.
     */
    @Test
    public void epicShouldHasTaskStatusNew() {
        final int epicId = taskManager.addNewEpic(epic);

        // First case, when epic has no subtasks
        assertEquals(NEW, epic.getStatus(), error + " вместо NEW, когда список подзадач Epic'а пустой");
        subtasks = createSubtasks(NEW, NEW, NEW, epicId);

        taskManager.addNewSubtask(subtasks.get(0));
        taskManager.addNewSubtask(subtasks.get(1));
        taskManager.addNewSubtask(subtasks.get(2));

        // Second case, when epic has subtasks with TaskStatus.NEW
        assertEquals(NEW, epic.getStatus(), error + "вместо NEW, когда все подзадачи имеет статус NEW");
    }

    @Test
    public void epicShouldHasTaskStatusInProgress() {
        final int epicId = taskManager.addNewEpic(epic);
        subtasks = createSubtasks(NEW, IN_PROGRESS, NEW, epicId);

        Subtask subtask1 = subtasks.get(0);
        Subtask subtask2 = subtasks.get(1);
        Subtask subtask3 = subtasks.get(2);

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        // First case when one subtask has TaskStatus.IN_PROGRESS, while others have TaskStatus.NEW
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач IN_PROGRESS, остальные NEW"
        );

        subtask1.setStatus(DONE);
        subtask3.setStatus(DONE);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask3);

        // Second case when one subtask has TaskStatus.IN_PROGRESS, while others have TaskStatus.DONE
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач IN_PROGRESS, остальные DONE"
        );

        subtask1.setStatus(NEW);
        subtask2.setStatus(NEW);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);

        // Third case when one subtask has TaskStatus.DONE, while others have TaskStatus.NEW
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда одна из подзадач DONE, остальные NEW"
        );

        subtask1.setStatus(IN_PROGRESS);
        subtask2.setStatus(IN_PROGRESS);
        subtask3.setStatus(IN_PROGRESS);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        // Fourth case when all subtasks have TaskStatus.IN_PROGRESS
        assertEquals(IN_PROGRESS, epic.getStatus()
                , error
                        + epic.getStatus().toString()
                        + "вместо IN_PROGRESS, когда все подзадачи IN_PROGRESS"
        );
    }

    @Test
    public void epicShouldHasTaskStatusDone() {
        final int epicId = taskManager.addNewEpic(epic);
        subtasks = createSubtasks(DONE, DONE, DONE, epicId);
        Subtask subtask1 = subtasks.get(0);
        Subtask subtask2 = subtasks.get(1);
        Subtask subtask3 = subtasks.get(2);

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        // If all subtasks have TaskStatus.DONE, then their Epic must have TaskStatus.DONE
        assertEquals(DONE, epic.getStatus(), error + epic.getStatus().toString() + " вместо DONE");
    }

    @Test
    public void addSubtaskToAnEpic() {
        final int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("subtaskName", "subtaskDescription", DONE, epicId);

        final int subtaskId = taskManager.addNewSubtask(subtask);

        assertTrue(epic.getSubtasks().contains(subtaskId)
                , "ID Subtask'а не был добавлен в список подзадач Epic'а");
    }

    @Test
    public void setSubtasksToAnEpic() {
        final int epicId = taskManager.addNewEpic(epic);
        subtasks = createSubtasks(DONE, DONE, DONE, epicId);

        Subtask subtask1 = subtasks.get(0);
        Subtask subtask2 = subtasks.get(1);
        Subtask subtask3 = subtasks.get(2);

        final int subtask1Id = taskManager.addNewSubtask(subtask1),
                subtask2Id = taskManager.addNewSubtask(subtask2),
                subtask3Id = taskManager.addNewSubtask(subtask3);

        List<Integer> subtasksIds = List.of(subtask1Id, subtask2Id, subtask3Id);

        epic.setSubtaskIds(subtasksIds);

        assertEquals(subtasksIds, epic.getSubtasks(), "Список подзадач Epic не был установлен корректно");
    }

    @Test
    public void addNewTask() {
        int taskId = taskManager.addNewTask(task);

        assertEquals(task, taskManager.getTask(taskId), "Manager does not contain added task");
        assertTrue(taskManager.getTasks().contains(task));
    }

    @Test
    public void addNewSubtask() {
        int epicId = taskManager.addNewEpic(epic);
        subtask = new Subtask("subtaskName", "subtaskDescription", NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        assertEquals(subtask, taskManager.getSubtask(subtaskId), "Manager does not contain added subtask");
        assertTrue(taskManager.getSubtasks().contains(subtask));
    }

    @Test
    public void addNewEpic() {
        int epicId = taskManager.addNewEpic(epic);

        assertEquals(epic, taskManager.getEpic(epicId), "Manager does not contain added epic");
        assertTrue(taskManager.getEpics().contains(epic));
    }

    @Test
    public void shouldNotReturnNoExistingTasks() {
        // No-existing task.Task
        assertNull(taskManager.getTask(1), "Returns no-existing task");

        // No-existing task.Subtask
        assertNull(taskManager.getSubtask(1), "Returns no-existing subtask");

        // No-existing task.Epic
        assertNull(taskManager.getEpic(1), "Returns no-existing epic");
    }

    @Test
    public void subtaskMustHaveEpic() {
        subtask = new Subtask("subtaskName", "subtaskDescription", NEW, 1);

        int subtaskId = taskManager.addNewSubtask(subtask);
        // Must return -1 if there is no such Epic with specified epicId
        assertEquals(-1, subtaskId);
    }


    // Tests for update functions
    @Test
    public void shouldNotUpdateNoExistingTask() {
        taskManager.deleteAll();
        taskManager.addNewTask(new Task("1", "2", NEW));
        int epicId = taskManager.addNewEpic(new Epic("1", "2", NEW));
        taskManager.addNewSubtask(new Subtask("1", "2", NEW, epicId));
        // No-existing task.Task
        taskManager.updateTask(new Task("very funny taskName", "taskDescription", NEW));
        List<String> names = taskManager
                .getTasks()
                .stream()
                .map(Task::getName)
                .collect(Collectors.toList());
        assertFalse(names.contains("very funny taskName"), "Updates no-existing task");
        // No-existing task.Subtask
        taskManager.updateSubtask(new Subtask("very funny subtaskName", "desc", NEW, 1));
        names = taskManager.getSubtasks()
                .stream()
                .map(Subtask::getName)
                .collect(Collectors.toList());
        assertFalse(names.contains("very funny subtaskName"), "Updates no-existing subtask");
        // No-existing task.Epic
        taskManager.updateEpic(new Epic("very funny epicName", "desc", NEW));
        names = taskManager.getEpics()
                .stream()
                .map(Epic::getName)
                .collect(Collectors.toList());
        assertFalse(names.contains("very funny epicName"), "Updates no-existing epic");
    }

    @Test
    public void updateTasks() {
        taskManager.deleteAll();

        Epic updateEpic = new Epic("very funny epicName", "desc", NEW);
        int epicId = taskManager.addNewEpic(updateEpic);
        Subtask updateSubtask = new Subtask("very funny subtaskName", "desc", NEW, epicId);
        taskManager.addNewSubtask(updateSubtask);
        Task updateTask = new Task("very funny taskName", "taskDescription", NEW);
        taskManager.addNewTask(updateTask);

        updateTask.setDescription("taskDesc2");
        taskManager.updateTask(updateTask);

        updateSubtask.setDescription("subtaskDesc2");
        taskManager.updateSubtask(updateSubtask);
        updateEpic.setDescription("epicDesc2");
        taskManager.updateEpic(updateEpic);

        assertTrue(taskManager.getTasks().contains(updateTask), "Does not update existing task");
        assertTrue(taskManager.getSubtasks().contains(updateSubtask), "Does not update existing subtask");
        assertTrue(taskManager.getEpics().contains(updateEpic), "Does not update existing epic");
    }

    // Tests for delete functions
    @Test
    public void shouldNotDeleteNoExistingTask() {
        // No-existing task.Task
        assertNull(taskManager.deleteTask(1), "Deletes no-existing task");
        // No-existing task.Subtask
        assertNull(taskManager.deleteSubtask(1), "Deletes no-existing subtask");
        // No-existing task.Epic
        assertNull(taskManager.deleteEpic(1), "Deletes no-existing epic");
    }

    @Test
    public void deleteTasks() {
        int taskId = taskManager.addNewTask(task);
        int epicId = taskManager.addNewEpic(epic);
        int subtask1Id = taskManager.addNewSubtask(new Subtask("subtaskName", "subtaskDescription",
                NEW, epicId));
        taskManager.addNewSubtask(new Subtask("subtaskName", "subtaskDescription", NEW, epicId));

        // Should return the object that is being deleted
        assertNotNull(taskManager.deleteTask(taskId), "Deletes no-existing task");
        assertNotNull(taskManager.deleteSubtask(subtask1Id), "Deletes no-existing subtask");
        assertNotNull(taskManager.deleteEpic(epicId), "Deletes no-existing epic");
    }

    // Test for setHistory
    @Test
    public void setHistory() {
        int taskId = taskManager.addNewTask(task);
        int epicId = taskManager.addNewEpic(epic);
        int subtaskId = taskManager.addNewSubtask(new Subtask("subtaskName", "subtaskDescription",
                NEW, epicId));

        List<Integer> history = List.of(taskId, epicId, subtaskId);

        taskManager.setHistory(history);

        assertEquals(history, taskManager.getHistory());
    }

    @Test
    public void timeIntervalCheck() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 20, 20, 10, 10);
        Task task1 = new Task("task 1", "taskDescription", NEW, dateTime, Duration.ofMinutes(40));
        Task task2 = new Task("task 2", "taskDescription",
                NEW, dateTime.plusMinutes(30), Duration.ofMinutes(10));
        Task task3 = new Task("task 3", "taskDescription",
                NEW, dateTime.minusMinutes(10), Duration.ofMinutes(10));
        Task task4 = new Task("task 4", "taskDescription",
                NEW, dateTime.minusMinutes(60), Duration.ofMinutes(60));
        Task task5 = new Task("task 5", "taskDescription", NEW);
        Task task6 = new Task("task 6", "taskDescription", NEW);

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewTask(task3);
        taskManager.addNewTask(task4);
        taskManager.addNewTask(task5);
        taskManager.addNewTask(task6);

        List<Task> check = List.of(task1, task3, task5, task6);

        assertEquals(check, taskManager.getTasks());
    }

    @Test
    public void shouldSortTasks() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 20, 20, 10, 10);
        Task task1 = new Task("task 1", "taskDescription",
                NEW, dateTime, Duration.ofMinutes(40));
        Task task2 = new Task("task 2", "taskDescription",
                NEW, dateTime.plusMinutes(40), Duration.ofMinutes(10));
        Task task3 = new Task("task 3", "taskDescription",
                NEW, dateTime.minusMinutes(10), Duration.ofMinutes(10));
        Task task4 = new Task("task 4", "taskDescription",
                NEW, dateTime.minusMinutes(60), Duration.ofMinutes(10));
        Task task5 = new Task("task 5", "taskDescription", NEW);
        Task task6 = new Task("task 6", "taskDescription", NEW);

        List<Integer> check = List.of(taskManager.addNewTask(task4),
                taskManager.addNewTask(task3),
                taskManager.addNewTask(task1),
                taskManager.addNewTask(task2),
                taskManager.addNewTask(task5),
                taskManager.addNewTask(task6));

        List<Integer> actual = taskManager
                .getPrioritizedTasks()
                .stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        assertEquals(check, actual);
    }

    @Test
    public void shouldSetEpicsTimeAndDuration() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 20, 20, 10, 10);

        int id = taskManager.addNewEpic(epic);
        subtask = new Subtask("subtask", "subtaskDescription",
                NEW, id, dateTime, Duration.ofMinutes(10));

        taskManager.addNewSubtask(subtask);

        assertEquals(dateTime, epic.getStartTime());
        assertEquals(Duration.ofMinutes(10), epic.getDuration());
        assertEquals(dateTime.plusMinutes(10), epic.getEndTime());
    }
}
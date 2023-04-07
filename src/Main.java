import manager.Managers;
import manager.TaskManager;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import static task.TaskStatus.NEW;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getInMemoryTask();

        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 20, 20, 10, 10);
        Task task1 = new Task("task 1", "taskDescription", NEW,
                dateTime, Duration.ofMinutes(40));
        Task task2 = new Task("task 2", "taskDescription",
                NEW, dateTime.plusMinutes(40), Duration.ofMinutes(10));
        Task task3 = new Task("task 3", "taskDescription",
                NEW, dateTime.minusMinutes(10), Duration.ofMinutes(10));
        Task task4 = new Task("task 4", "taskDescription",
                NEW, dateTime.minusMinutes(60), Duration.ofMinutes(10));
        Task task5 = new Task("task 5", "taskDescription", NEW);
        Task task6 = new Task("task 6", "taskDescription", NEW);

        taskManager.addNewTask(task6);

        TreeSet<Task> check = new TreeSet<>((o1, o2) -> {
            if (o2.getStartTime() == null) {
                return 1;
            } else if (o1.getStartTime() == null) {
                return 1;
            } else if (o1.getStartTime() == null && o2.getStartTime() == null) {
                return 0;
            }

            Duration between = Duration.between(o1.getStartTime(), o2.getStartTime());

            if (between.isNegative()) {
                return 1;
            } else if (between.isZero()) {
                return 0;
            } else {
                return -1;
            }
        });

        check.addAll(List.of(task6));
        TreeSet<Task> actual = taskManager.getPrioritizedTasks();

        boolean checker = check.contains(task6);

        System.out.println(checker);
    }
}

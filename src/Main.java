import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("resources/tasks.csv");

        TaskManager managerWriter = Managers.getDefault(path.toFile());

        Task task1 = new Task("Таска1", "Обычная таска", TaskStatus.NEW, LocalDateTime.now(), Duration.ofMinutes(10));
        final int task1Id = managerWriter.addNewTask(task1);
        Task task2 = new Task("Таска2", "Очень обычная таска", TaskStatus.IN_PROGRESS, LocalDateTime.now().plusDays(10), Duration.ofMinutes(10));
        final int task2Id = managerWriter.addNewTask(task2);
        Task task3 = new Task("Таска3", "Очень обычная таска", TaskStatus.IN_PROGRESS);
        managerWriter.addNewTask(task3);

        Task task4 = new Task("Таска4", "Очень обычная таска", TaskStatus.IN_PROGRESS);
        managerWriter.addNewTask(task4);

        Epic epic1 = new Epic("Эпик1", "Эпик", TaskStatus.NEW);
        final int epic1Id = managerWriter.addNewEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Subtask1", TaskStatus.NEW, epic1.getId(), LocalDateTime.now().plusHours(10), Duration.ofMinutes(10));
        final int subtask1Id = managerWriter.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаска 2", "Subtask2", TaskStatus.IN_PROGRESS, epic1.getId(), LocalDateTime.now().minusHours(5), Duration.ofMinutes(10));
        final int subtask2Id = managerWriter.addNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("Сабтаска 3", "Subtask3", TaskStatus.IN_PROGRESS, epic1.getId(), LocalDateTime.now().plusMinutes(15), Duration.ofMinutes(10));
        final int subtask3Id = managerWriter.addNewSubtask(subtask3);

        Epic epic2 = new Epic("Эпик2", "Эпик2", TaskStatus.NEW);
        final int epic2Id = managerWriter.addNewEpic(epic2);

        TreeSet<Task> taskSet = managerWriter.getPrioritizedTasks();

        taskSet.forEach(System.out::println);
        System.out.println(epic1.getStartTime());
        System.out.println(epic1.getEndTime());
    }
}

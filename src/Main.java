import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import task.*;
import custom.tests.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("resources/tasks.csv");

        TaskManager managerWriter = Managers.getDefault(path.toFile());

        Task task1 = new Task("Таска1", "Обычная таска", TaskStatus.NEW);
        final int task1Id = managerWriter.addNewTask(task1);
        Task task2 = new Task("Таска2", "Очень обычная таска", TaskStatus.IN_PROGRESS);
        final int task2Id = managerWriter.addNewTask(task2);

        Epic epic1 = new Epic("Эпик1", "Эпик", TaskStatus.NEW);
        final int epic1Id = managerWriter.addNewEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Subtask1", TaskStatus.NEW, epic1.getId());
        final int subtask1Id = managerWriter.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаска 2", "Subtask2", TaskStatus.IN_PROGRESS, epic1.getId());
        final int subtask2Id = managerWriter.addNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("Сабтаска 3", "Subtask3", TaskStatus.IN_PROGRESS, epic1.getId());
        final int subtask3Id = managerWriter.addNewSubtask(subtask3);

        Epic epic2 = new Epic("Эпик2", "Эпик2", TaskStatus.NEW);
        final int epic2Id = managerWriter.addNewEpic(epic2);

        managerWriter.getTask(task1Id);
        managerWriter.getEpic(epic2Id);
        managerWriter.getSubtask(subtask3Id);

        System.out.println("\t\tTest FileBackedTasksManager");

        FileBackedTasksManager managerReader = FileBackedTasksManager.loadFromFile(path.toFile());

        List<Task> tasksFromWriter = managerWriter.getTasks();
        List<Epic> epicsFromWriter = managerWriter.getEpics();
        List<Subtask> subtasksFromWriter = managerWriter.getSubtasks();

        assert managerReader != null;
        List<Task> tasksFromReader = managerReader.getTasks();
        List<Epic> epicsFromReader = managerReader.getEpics();
        List<Subtask> subtasksFromReader = managerReader.getSubtasks();

        Test.Test_FileBackedManager(tasksFromWriter, tasksFromReader,
                subtasksFromWriter, subtasksFromReader,
                epicsFromWriter, epicsFromReader);

        Files.move(path, Paths.get("resources", "test_data", path.getFileName().toString()),
                REPLACE_EXISTING);

    }
}

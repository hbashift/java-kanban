import exceptions.ManagerSaveException;
import manager.FileBackedManager;
import manager.Managers;
import manager.TaskManager;
import task.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

/*        Task task1 = new Task("Таска1", "Обычная таска", TaskStatus.NEW);
        final int task1Id = manager.addNewTask(task1);
        Task task2 = new Task("Таска2", "Очень обычная таска", TaskStatus.IN_PROGRESS);
        final int task2Id = manager.addNewTask(task2);

        Epic epic1 = new Epic("Эпик1", "Эпик", TaskStatus.NEW);
        final int epic1Id = manager.addNewEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Subtask1", TaskStatus.NEW, epic1.getId());
        final int subtask1Id = manager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаска 2", "Subtask2", TaskStatus.IN_PROGRESS, epic1.getId());
        final int subtask2Id = manager.addNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("Сабтаска 3", "Subtask3", TaskStatus.IN_PROGRESS, epic1.getId());
        final int subtask3Id = manager.addNewSubtask(subtask3);

        Epic epic2 = new Epic("Эпик2", "Эпик2", TaskStatus.NEW);
        final int epic2Id = manager.addNewEpic(epic2);

        manager.getTask(task1Id);
        manager.getEpic(epic2Id);
        manager.getSubtask(subtask2Id);*/

        manager.printAll();

        System.out.println("\n\n\t\tTest FileBackedManager");
        FileBackedManager testManager = new FileBackedManager(new File("resources/tasks.csv"));

        try {
            testManager.loadFromFile();
        } catch (IOException | ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        testManager.printAll();
    }
}

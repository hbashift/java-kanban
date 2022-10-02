package tests;

import java.util.List;
import manager.TaskManager;
import task.*;

public class Test {

    public static void test1(TaskManager manager) {
        System.out.println("----------PRINTING ALL TYPES OF TASKS----------");
        manager.printAll();
        System.out.println('\n');
    }

    public static void test2(TaskManager manager) {
        System.out.println("----------CHECKING manager.getHistory()----------");
        System.out.println("1) " + manager.getEpic(3));
        System.out.println("2) " + manager.getTask(1));
        System.out.println("3) " + manager.getEpic(3));
        System.out.println("4) " + manager.getSubtask(7));
        System.out.println("5) " + manager.getTask(2));
        System.out.println("6) " + manager.getEpic(3));
        System.out.println("7) " + manager.getTask(1));
        System.out.println("8) " + manager.getEpic(3));
        System.out.println("9) " + manager.getSubtask(7));
        System.out.println("10) " + manager.getTask(2));
        System.out.println("11) " + manager.getTask(2));
        System.out.println('\n');

        System.out.println("Printing history of browsing the tasks");
        List<Task> taskList = manager.getHistory();
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i+1) + ") " + taskList.get(i).toString());
        }

        System.out.println('\n');
    }

    public static void test3(TaskManager manager, Task task1, Task task2, Subtask subtask1, Subtask subtask2, Subtask subtask3) {
        System.out.println("----------Status change: ----------\n"
        + "Task1 status changes from " + task1.getStatus() + " to " + TaskStatus.IN_PROGRESS + '\n'
        + "Task2 status changes from " + task2.getStatus() + " to " + TaskStatus.DONE + '\n'
        + "Subtask1 status changes from " + subtask1.getStatus() + " to " + TaskStatus.IN_PROGRESS + '\n'
        + "Subtask2 status changes from " + subtask2.getStatus() + " to " + TaskStatus.DONE + '\n'
        + "Subtask3 status changes from " + subtask3.getStatus() + " to " + TaskStatus.DONE + '\n');

        task1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task1);

        task2.setStatus(TaskStatus.DONE);
        manager.updateTask(task2);

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("After Status changed: ");
        manager.printAll();
        System.out.println('\n');
    }

    public static void test4(TaskManager manager, int epicId, int taskId, int subtaskId) {
        System.out.println("----------Deleting tasks with ids: ----------\n"
                + "Epic Id: " + epicId + '\n'
                + "Task Id: " + taskId + '\n'
                + "Subtask id: " + subtaskId + '\n');
        manager.deleteEpic(epicId);
        manager.deleteSubtask(subtaskId);
        manager.deleteTask(taskId);

        System.out.println("After deleting");
        manager.printAll();
    }
}

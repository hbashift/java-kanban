import manager.Manager;
import task.*;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Таска1", "Обычная таска", Status.NEW);
        final int task1Id = manager.addNewTask(task1);
        Task task2 = new Task("Таска2", "Очень обычная таска", Status.IN_PROGRESS);
        final int task2Id = manager.addNewTask(task2);

        Epic epic1 = new Epic("Эпик1", "Эпик", Status.NEW);
        final int epic1Id = manager.addNewEpic(epic1);

        Subtask subtask1 = new Subtask("Сабтаска 1", "Обычная сабтаска", Status.NEW, epic1);
        final int subtask1Id = manager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Сабтаска 2", "Очень обычная сабтаска", Status.IN_PROGRESS, epic1);
        final int subtask2Id = manager.addNewSubtask(subtask2);

        Epic epic2 = new Epic("Эпик2", "Эпик2", Status.NEW);
        final int epic2Id = manager.addNewEpic(epic2);

        Subtask subtask3 = new Subtask("Сабтаска3", "Прям обычная сабтаска", Status.NEW, epic2);
        final int subtask3Id = manager.addNewSubtask(subtask3);

        System.out.println("До изменения статуса");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);

        task2.setStatus(Status.DONE);
        manager.updateTask(task2);

        subtask1.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("\nПосле изменения статуса");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.deleteEpicById(epic1Id);

        System.out.println("\nПосле удаление эпика с id = " + epic1Id);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
    }
}

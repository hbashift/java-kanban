package manager.file;

import exceptions.ManagerSaveException;
import manager.memory.InMemoryTasksManager;
import task.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTasksManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager() {
        this.file = Path.of("resources/tasks.csv").toFile();
    }

    // loads saved task/Task, task/Subtask/ task/Epic from a csv file
    public static FileBackedTasksManager loadFromFile(File file) {
        String lines;

        try {
            lines = Files.readString(file.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new FileBackedTasksManager(file);
        }

        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        if (lines.length() == 0) {
            System.out.println("File is empty");
            return new FileBackedTasksManager(file);
        }

        String[] tasks = lines.split("\n");

        for (int i = 1; i < tasks.length - 2; i++) {
            String[] insides = tasks[i].split(",");
            manager.fromString(insides);
        }

        String[] history = tasks[tasks.length - 1].split(",");
        if (history.length > 0) {
            manager.setHistory(CSVFormatter.historyFromString(history));
        }

        manager.generatorId = tasks.length - 2;

        return manager;
    }

    // creates task/Task, task/Subtask/, task/Epic after parsing them from a csv file
    // and then adds them in a corresponding maps
    private void fromString(String[] insides) {
        switch (TaskType.valueOf(insides[1])) {
            case TASK:
                Task task;
                if (insides.length == 7)
                    task = new Task(insides[2],
                            insides[4],
                            TaskStatus.valueOf(insides[3]),
                            LocalDateTime.parse(insides[5], Task.formatter()),
                            Duration.parse(insides[6]));
                else
                    task = new Task(insides[2],
                            insides[4],
                            TaskStatus.valueOf(insides[3]));

                super.addNewTask(task, Integer.parseInt(insides[0]));
                break;

            case EPIC:
                Epic epic = new Epic(insides[2],
                        insides[4],
                        TaskStatus.valueOf(insides[3]));
                super.addNewEpic(epic, Integer.parseInt(insides[0]));
                break;

            case SUBTASK:
                Subtask subtask;
                if (insides.length == 8)
                    subtask = new Subtask(insides[2],
                            insides[4],
                            TaskStatus.valueOf(insides[3]),
                            Integer.parseInt(insides[5]),
                            LocalDateTime.parse(insides[6], Task.formatter()),
                            Duration.parse(insides[7]));
                else
                    subtask = new Subtask(insides[2],
                            insides[4],
                            TaskStatus.valueOf(insides[3]),
                            Integer.parseInt(insides[5]));

                super.addNewSubtask(subtask, Integer.parseInt(insides[0]));
                break;
        }
    }

    // save function. saves the current condition of tasks in a file
    private void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            List<Task> tasks = super.getTasks();
            List<Subtask> subtasks = super.getSubtasks();
            List<Epic> epics = super.getEpics();
            List<Integer> history = super.getHistory();
            CSVFormatter.allToString(tasks, subtasks, epics, history, writer);

            if (Objects.nonNull(tasks))
                tasks.clear();
            if (Objects.nonNull(subtasks))
                subtasks.clear();
            if (Objects.nonNull(epics))
                epics.clear();
            if (Objects.nonNull(history))
                history.clear();

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    // try catch wrapper over function FileBackedTasksManager.save();
    private void saver() {
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    // Override functions from InMemoryTasksManager
    // calling super.{functionName}, and then saves condition in a file
    // for add, update, get, delete functions
    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        saver();

        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);
        saver();

        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        saver();

        return id;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        saver();

        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        saver();

        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        saver();

        return epic;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        saver();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        saver();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        saver();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();

        saver();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();

        saver();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();

        saver();
    }

    @Override
    public Task deleteTask(int id) {
        Task task = super.deleteTask(id);
        saver();

        return task;
    }

    @Override
    public Subtask deleteSubtask(int id) {
        Subtask subtask = super.deleteSubtask(id);
        saver();

        return subtask;
    }

    @Override
    public Epic deleteEpic(int id) {
        Epic epic = super.deleteEpic(id);
        saver();

        return epic;
    }
}

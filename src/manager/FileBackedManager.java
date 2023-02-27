package manager;

import exceptions.ManagerSaveException;
import task.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class FileBackedManager extends InMemoryTasksManager {
    private final File file;

    public FileBackedManager(File file) {
        this.file = file;
    }

    public void loadFromFile() throws IOException, ManagerSaveException {
        String lines = Files.readString(file.toPath());

        if (lines.length() == 0) {
            throw new ManagerSaveException("File is empty");
        }

        String[] tasks = lines.split("\n");

        for (int i = 1; i < tasks.length - 2; i++) {
            String[] insides = tasks[i].split(",");
            fromString(insides);
        }

        String[] history = tasks[tasks.length - 1].split(",");
        if (history.length > 0) {
            super.setHistory(CSVFormatter.historyFromString(history, this));
        }
    }

    private void fromString(String[] insides) {
        switch (TaskType.valueOf(insides[1])) {
            case TASK:
                Task task = new Task(insides[2]
                        , insides[4]
                        ,TaskStatus.valueOf(insides[3]));
                super.addNewTask(task);
                break;

            case EPIC:
                Epic epic = new Epic(insides[2],
                        insides[4],
                        TaskStatus.valueOf(insides[3]));
                super.addNewEpic(epic);
                break;

            case SUBTASK:
                Subtask subtask = new Subtask(insides[2]
                        , insides[4]
                        , TaskStatus.valueOf(insides[3])
                        , Integer.parseInt(insides[5]));
                super.addNewSubtask(subtask);
                break;
        }
    }

    private void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            List<Task> tasks = super.getAllTasks();
            List<Subtask> subtasks = super.getAllSubtasks();
            List<Epic> epics = super.getAllEpics();
            List<Integer> history = super.getHistory();
            CSVFormatter.allToString(tasks, subtasks, epics, history, writer);

            tasks.clear();
            subtasks.clear();
            epics.clear();
            history.clear();

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);

        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }

        return epic;
    }
}

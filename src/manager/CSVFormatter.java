package manager;

import task.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {

    public static String toString(Task task) {
        return task.getId() + ","
                + task.getType() + ","
                + task.getName() + ","
                + task.getStatus() + ","
                + task.getDescription() + ",";

    }

    public static String toString(Subtask subtask) {
        return subtask.getId() + ","
                + subtask.getType() + ","
                + subtask.getName() + ","
                + subtask.getStatus() + ","
                + subtask.getDescription() + ","
                + subtask.getEpicId();
    }

    public static String toString(Epic epic) {
        return epic.getId() + ","
                + epic.getType() + ","
                + epic.getName() + ","
                + epic.getStatus() + ","
                + epic.getDescription() + ",";
    }

    public static void allToString(List<Task> tasks,
                                   List<Subtask> subtasks,
                                   List<Epic> epics,
                                   List<Integer> history,
                                   BufferedWriter writer) throws IOException {
        StringBuilder builder = new StringBuilder();

        String format = "id,type,name,status,description,epic";
        writer.write(format + '\n');

        for (Task task : tasks) {
            writer.write(CSVFormatter.toString(task) + '\n');
        }

        for (Epic epic : epics) {
            writer.write(CSVFormatter.toString(epic) + '\n');
        }

        for (Subtask subtask : subtasks) {
            writer.write(CSVFormatter.toString(subtask) + '\n');
        }

        writer.write('\n');

        history.forEach(id -> builder.append(id).append(','));
        writer.write(builder.toString());
    }

    public static List<Integer> historyFromString(String[] insides, FileBackedManager manager) {
        List<Integer> history = new ArrayList<>();

        for (String id : insides) {
            history.add(Integer.parseInt(id));
        }

        return history;
    }
}

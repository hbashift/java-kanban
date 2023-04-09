package manager.file;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CSVFormatter {
    // toString() functions
    // serializing task.Task/Subtask/Epic objects into CSV format
    public static String toString(Task task) {
        String str;

        if (task.getStartTime() == null)
            str = task.getId() + ","
                    + task.getType() + ","
                    + task.getName() + ","
                    + task.getStatus() + ","
                    + task.getDescription();
        else
            str = task.getId() + ","
                    + task.getType() + ","
                    + task.getName() + ","
                    + task.getStatus() + ","
                    + task.getDescription() + ","
                    + task.getStartTime().format(Task.formatter()) + ","
                    + task.getDuration().toString();

        return str;
    }

    public static String toString(Subtask subtask) {
        String str;

        if (subtask.getStartTime() == null)
            str = subtask.getId() + ","
                    + subtask.getType() + ","
                    + subtask.getName() + ","
                    + subtask.getStatus() + ","
                    + subtask.getDescription() + ","
                    + subtask.getEpicId();
        else
            str = subtask.getId() + ","
                    + subtask.getType() + ","
                    + subtask.getName() + ","
                    + subtask.getStatus() + ","
                    + subtask.getDescription() + ","
                    + subtask.getEpicId() + ","
                    + subtask.getStartTime().format(Task.formatter()) + ","
                    + subtask.getDuration().toString();

        return str;
    }

    public static String toString(Epic epic) {
        String str;

        if (epic.getStartTime() == null)
            str = epic.getId() + ","
                    + epic.getType() + ","
                    + epic.getName() + ","
                    + epic.getStatus() + ","
                    + epic.getDescription();
        else
            str = epic.getId() + ","
                    + epic.getType() + ","
                    + epic.getName() + ","
                    + epic.getStatus() + ","
                    + epic.getDescription() + ","
                    + epic.getStartTime().format(Task.formatter()) + ","
                    + epic.getDuration().toString() + ","
                    + epic.getEndTime().format(Task.formatter());

        return str;
    }

    // Multiple conversion
    // params: lists of Task, Subtask, Epic, history (ids) and BufferedWriter to write in a file
    public static void allToString(List<Task> tasks,
            List<Subtask> subtasks,
            List<Epic> epics,
            List<Integer> history,
            BufferedWriter writer)
            throws IOException {
        StringBuilder builder = new StringBuilder();

        String format = "id,type,name,status,description,epic";
        writer.write(format + '\n');

        if (Objects.nonNull(tasks) && !tasks.isEmpty())
            for (Task task : tasks) {
                writer.write(CSVFormatter.toString(task) + '\n');
            }

        if (Objects.nonNull(epics) && !epics.isEmpty())
            for (Epic epic : epics) {
                writer.write(CSVFormatter.toString(epic) + '\n');
            }

        if (Objects.nonNull(subtasks) && !subtasks.isEmpty())
            for (Subtask subtask : subtasks) {
                writer.write(CSVFormatter.toString(subtask) + '\n');
            }

        writer.write('\n');

        if (!history.isEmpty()) {
            history.forEach(id -> builder.append(id).append(','));
        } else {
            builder.append(-1);
        }

        writer.write(builder.toString());
    }

    // converts history from a string to a List<Integer>, where elements of the list - id's of viewed tasks
    public static List<Integer> historyFromString(String[] insides) {
        List<Integer> history = new ArrayList<>();

        if (Arrays.stream(insides).anyMatch(e -> Integer.parseInt(e) == -1))
            return history;

        for (String id : insides)
            history.add(Integer.parseInt(id));

        return history;
    }
}

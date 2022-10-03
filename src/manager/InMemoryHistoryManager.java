package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void addTask(Task task) {
        if (history.size() != 10) {
            history.add(task);
        } else {
            history.remove(0);
            history.add(task);
        }
    }
}

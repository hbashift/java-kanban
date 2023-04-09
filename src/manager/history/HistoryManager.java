package manager.history;

import task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Integer> getHistory();

    Task remove(int id);

    void removeAll();

    void printAll();
}

package manager;

import task.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Integer> getHistory();

    void remove(int id);

    void removeAll();

    void printAll();
}

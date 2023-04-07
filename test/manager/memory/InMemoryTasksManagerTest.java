package manager.memory;

import manager.TaskManagerTest;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> {
    // Тесты для sortedTasks находится в TaskManager timeIntervalCheck() и shouldSortTasks()
    @BeforeEach
    public void setUp() {
        this.taskManager = new InMemoryTasksManager();
    }
}

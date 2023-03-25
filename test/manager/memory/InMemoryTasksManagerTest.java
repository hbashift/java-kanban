package manager.memory;

import manager.TaskManagerTest;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> {

    @BeforeEach
    public void setUp() {
        this.taskManager = new InMemoryTasksManager();
    }
}

package task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {
    private Task task;

    @Test
    public void setters() {
        task = new Task();
        task.setName("name");
        assertEquals("name", task.getName());

        task.setStatus(TaskStatus.NEW);
        assertEquals(TaskStatus.NEW, task.getStatus());

        task.setDescription("desc");
        assertEquals("desc", task.getDescription());

        task.setId(1);
        assertEquals(1, task.getId());
    }

    @Test
    public void getters() {
        task = new Task("name", "desc", TaskStatus.NEW);
        task.setId(1);

        assertEquals(1, task.getId());
        assertEquals("name", task.getName());
        assertEquals("desc", task.getDescription());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }
}

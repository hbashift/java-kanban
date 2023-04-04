package task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubtaskTest {
    private Subtask subtask;

    @Test
    public void setAndGetEpicId() {
        subtask = new Subtask("name", "desc", TaskStatus.NEW, 1
                , LocalDateTime.now(), Duration.ofMinutes(10));
        assertEquals(1, subtask.getEpicId());

        subtask.setEpicId(3);
        assertEquals(3, subtask.getEpicId());
    }

}

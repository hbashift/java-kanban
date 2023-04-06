package task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected TaskStatus taskStatus;
    protected TaskType type = TaskType.TASK;
    protected LocalDateTime startTime;
    protected Duration duration;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm:ss:SS");
    // default constructor and constructors with params
    public Task(){
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public Task(String name, String description, TaskStatus taskStatus, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
        this.startTime = startTime;
        this.duration = duration;
    }

    // getters and setters for class attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return taskStatus;
    }

    public void setStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskType getType() {
        return type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public static DateTimeFormatter formatter() {
        return formatter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        Task.formatter = formatter;
    }

    public static boolean isWithinRange(Task task1, Task task2) {
        return task1.getEndTime().isAfter(task2.startTime) && task1.startTime.isBefore(task2.getEndTime());
    }

    // hashCode, equals, toString override
    @Override
    public int hashCode() {
        int hash = 17;

        if (name != null)
            hash += name.hashCode();

        hash *= 31;

        if (description != null)
            hash += description.hashCode();

        hash *= 31;

        if (taskStatus != null)
            hash += taskStatus.hashCode();

        hash *= 31;

        if (id != null)
            hash += id.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task comparedTask = (Task) obj;
        return Objects.equals(id, comparedTask.id)
                && Objects.equals(name, comparedTask.name)
                && Objects.equals(description, comparedTask.description)
                && Objects.equals(taskStatus, comparedTask.taskStatus);
    }

    @Override
    public String toString() {
        if (startTime == null) {
            return "Task{"
                    + "name: " + name
                    + ", description: " + description
                    + ", id: " + id
                    + ", taskStatus: " + taskStatus
                    + ", startTime: null}";
        } else {
            return "Task{"
                    + "name: " + name
                    + ", description: " + description
                    + ", id: " + id
                    + ", taskStatus: " + taskStatus
                    + ", startTime: " + startTime.format(formatter) + "}";
        }
    }
}

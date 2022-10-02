package task;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected TaskStatus taskStatus;

    // default constructor and constructor with params
    public Task(){
    }

    public Task(String name, String description, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
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
        if (obj == null && getClass() != obj.getClass()) return false;
        Task comparedTask = (Task) obj;
        return Objects.equals(id, comparedTask.id)
                && Objects.equals(name, comparedTask.name)
                && Objects.equals(description, comparedTask.description)
                && Objects.equals(taskStatus, comparedTask.taskStatus);
    }

    @Override
    public String toString() {
        return "Task{"
                + "name: " + name
                + ", description: " + description
                + ", id: " + id
                + ", taskStatus: " + taskStatus + "}";
    }
}

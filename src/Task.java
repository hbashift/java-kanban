import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;
    // default constructor and constructor with params
    public Task(){
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

        if (status != null)
            hash += status.hashCode();

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
                && Objects.equals(status, comparedTask.status);
    }

    @Override
    public String toString() {
        return "Task{"
                + "name: " + name
                + ", description: " + description
                + ", id: " + id
                + ", status: " + status + "}";
    }
}

package task;

import java.util.Objects;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epicId = epic.getId();
    }
    // getter and setter for epicId to whom subtask belongs
    public int getEpicId() {
        return epicId;
    }

    public void setEpicid(int epicId) {
        this.epicId = epicId;
    }

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

        hash *= 31;

        if (epicId != null)
            hash += epicId.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null && getClass() != obj.getClass()) return false;
        Subtask comparedSubtask = (Subtask) obj;
        return Objects.equals(id, comparedSubtask.id)
                && Objects.equals(name, comparedSubtask.name)
                && Objects.equals(description, comparedSubtask.description)
                && Objects.equals(status, comparedSubtask.status)
                && Objects.equals(epicId, comparedSubtask.epicId);
    }

    @Override
    public String toString(){
        return "Subtask{"
                + "name: " + name
                + ", description: " + description
                + ", id: " + id
                + ", status: " + status
                + ", epicId: " + epicId + "}";
    }
}

package task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

    public Epic(String name, String description, TaskStatus taskStatus) {
        super(name, description, taskStatus);
        this.subtaskIds = new ArrayList<>();
    }
    //
    public void addSubtaskId(Subtask subtask) {
        subtaskIds.add(subtask.getId());
    }
    // getter and setter for subtaskIds
    public ArrayList<Integer> getSubtasks() {
        return subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
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

        if (taskStatus != null)
            hash += taskStatus.hashCode();

        hash *= 31;

        if (id != null)
            hash += id.hashCode();

        hash *= 31;

        if (subtaskIds != null)
            hash += subtaskIds.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null && getClass() != obj.getClass()) return false;
        Epic comparedEpic = (Epic) obj;
        return Objects.equals(id, comparedEpic.id)
                && Objects.equals(name, comparedEpic.name)
                && Objects.equals(description, comparedEpic.description)
                && Objects.equals(taskStatus, comparedEpic.taskStatus)
                && Objects.equals(subtaskIds, comparedEpic.subtaskIds);
    }

    @Override
    public String toString(){
        return "Epic{"
                + "name: " + name
                + ", description: " + description
                + ", id: " + id
                + ", taskStatus: " + taskStatus
                + ", subtaskIds: " + subtaskIds + "}";
    }
}

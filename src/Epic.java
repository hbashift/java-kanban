import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
        this.subtaskIds = new ArrayList<>();
    }
    //
    public void addSubtaskId(Subtask subtask) {
        subtaskIds.add(subtask.getId());
    }
    // getter for subtaskIds
    public ArrayList<Integer> getSubtasks() {
        return subtaskIds;
    }

    @Override
    public String toString(){
        return "Epic{"
                + "name: " + name
                + ", description: " + description
                + ", id: " + id
                + ", status: " + status
                + ", subtaskIds: " + subtaskIds + "}";
    }
}

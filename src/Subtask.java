public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epicId = epic.getId();
    }
    // getter for epicId to whom subtask belongs
    public int getEpicId() {
        return epicId;
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

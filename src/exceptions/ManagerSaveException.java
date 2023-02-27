package exceptions;

public class ManagerSaveException extends Exception {
    public ManagerSaveException(Exception e) {
        super(e);
    }

    public ManagerSaveException(String e) {
        super(e);
    }
}

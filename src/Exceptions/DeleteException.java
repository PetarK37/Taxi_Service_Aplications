package Exceptions;

public class DeleteException extends Throwable {
    String message;
    public DeleteException(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}

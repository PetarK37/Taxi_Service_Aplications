package Exceptions;

public class UpdateException extends Throwable {
    String message;

    public UpdateException(String s) {
        this.message = s;
    }

    public String getMessage(){
        return message;
    }
}

package Exceptions;

public class LoadException extends RuntimeException {

    public LoadException(Exception e) {
        super(e);
    }
}


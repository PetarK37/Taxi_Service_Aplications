package Exceptions;

public class SaveExcption extends RuntimeException {

    public SaveExcption(Exception e) {
        super(e);
    }
}

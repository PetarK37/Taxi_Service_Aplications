package Exceptions;

public class FileErrorExeption extends RuntimeException {
    String problematicLine;
    public FileErrorExeption(String s) {
        this.problematicLine = s;
    }

    public String getProblematicLine() {
        return problematicLine;
    }
}

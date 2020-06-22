package exception;

public class LineLgthHigherThanWidth extends Exception {
    public LineLgthHigherThanWidth(int currentLine) {
        super(String.format("The number of characters on current line (%d) is higher than width", currentLine));
    }
}

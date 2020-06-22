package exception;


import configuration.GameConfiguration;

public class HeightNotValidException extends Exception {
    public HeightNotValidException() {
        super("Height must be less than or equal to "+ GameConfiguration.HEIGHT_MAX);
    }
}

package exception;

import configuration.GameConfiguration;

public class WidthNotValidException extends Exception {
    public WidthNotValidException() {
        super("Width has to be higher than "+ GameConfiguration.WIDTH_MIN);
    }
}

package it.unicam.cs.pa.robot;

/**
 * Custom exception class for handling robot-related exceptions.
 */
public class RobotException extends RuntimeException {
    /**
     * Constructs a new instance of the RobotException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public RobotException(String message) {
        super(message);
    }
}

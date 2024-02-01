package it.unicam.cs.pa.environment;

/**
 * Custom exception class for handling position-related exceptions.
 */
public class PositionException extends EnvironmentException {
    /**
     * Constructs a new instance of the PositionException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public PositionException(String message) {
        super(message);
    }
}

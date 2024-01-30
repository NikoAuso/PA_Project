package it.unicam.cs.pa.environment;

/**
 * Custom exception class for handling shape-related exceptions.
 */
public class EnvironmentException extends RuntimeException {
    /**
     * Constructs a new instance of the ShapeException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public EnvironmentException(String message) {
        super(message);
    }
}

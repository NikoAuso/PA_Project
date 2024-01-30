package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.EnvironmentException;

/**
 * Custom exception class for handling shape-related exceptions.
 */
public class ShapeException extends EnvironmentException {
    /**
     * Constructs a new instance of the ShapeException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public ShapeException(String message) {
        super(message);
    }
}

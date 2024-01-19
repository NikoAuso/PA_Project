package it.unicam.cs.pa.entities;

/**
 * Exception for the position
 */
public class PositionException extends RuntimeException {
    /**
     * Position exception constructor with message.
     * @param message message to be reported
     */
    public PositionException(String message) {
        super(message);
    }
}

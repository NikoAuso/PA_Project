package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.robot.RobotException;

/**
 * Custom exception class for handling robot command exceptions.
 */
public class CommandException extends RobotException {
    /**
     * Constructs a new instance of the RobotException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public CommandException(String message) {
        super(message);
    }
}

package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;

/**
 * An interface representing a command that can be executed by a robot.
 */
public interface Command {
    /**
     * Executes the command on the specified environment and robot.
     *
     * @param environment The environment in which the command is executed.
     * @param robot       The robot on which the command is executed.
     * @throws CommandException If an error occurs while executing the command.
     */
    void execute(Environment environment, Robot robot) throws CommandException;

    /**
     * Gets the type of the command.
     *
     * @return The type of the command.
     */
    Class<? extends Command> getType();
}

package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.robot.Robot;

/**
 * An interface representing a command that can be executed by a robot.
 */
public interface Command {
    /**
     * Executes the command on a specified robot within a given environment.
     *
     * @param robot The robot on which the command is executed.
     */
    void execute(Robot robot) throws CommandException;
}

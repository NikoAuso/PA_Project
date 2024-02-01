package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;

/**
 * An interface representing a command that can be executed by a robot.
 */
public interface Command {
    void execute(Environment environment, Robot robot) throws CommandException;
}

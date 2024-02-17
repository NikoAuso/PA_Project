package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the UNSIGNAL command. The robot that executes this commands stop signaling the passed label.
 * It implements the {@link MovementCommand} interface.
 *
 * @param label The label to be signaled by the robot.
 */
public record Unsignal(String label) implements MovementCommand {
    /**
     * Executes the Unsignal command by remove the specified label.
     *
     * @param environment the environment in which the robot operates
     * @param robot       the robot executing the command
     * @throws CommandException if an error occurs during command execution
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.unsignal(label);
    }
}

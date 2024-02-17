package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the SIGNAL command. The robot that executes this commands start signaling the passed label.
 * It implements the {@link MovementCommand} interface.
 *
 * @param label The label to be signaled by the robot.
 */
public record Signal(String label) implements MovementCommand {
    /**
     * Executes the Signal command by signaling the specified label.
     *
     * @param environment the environment in which the robot operates
     * @param robot       the robot executing the command
     * @throws CommandException if an error occurs during command execution
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.signal(label);
    }
}

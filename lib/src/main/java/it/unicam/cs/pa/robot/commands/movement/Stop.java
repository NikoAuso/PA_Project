package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the STOP command. The robot that executes this commands stop the movement of the robot.
 * It implements the {@link MovementCommand} interface.
 */
public record Stop() implements MovementCommand {
    /**
     * Stops the movement of the robot.
     *
     * @param environment the environment in which the robot operates (unused)
     * @param robot       the robot executing the command
     * @throws CommandException if an error occurs during command execution
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.stop();
    }
}

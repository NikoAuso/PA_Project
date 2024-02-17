package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the CONTINUE command. This class create a loop for the robot that continue to move at same
 * direction and speed for a specified duration.
 * It implements the {@link LoopCommand} interface.
 *
 * @param duration      the number of steps in which the robot continues to move with the same direction and speed
 * @param startingIndex the command index in the list of commands
 */
public record Continue(int duration, int startingIndex) implements LoopCommand {
    /**
     * Executes the CONTINUE command on the specified environment and robot.
     *
     * @param environment The environment in which the robot operates.
     * @param robot       The robot executing the command.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.pushLoopCount(duration, startingIndex);
        robot.setFinishLoopIndex();
        robot.resetLoopIndex();
        robot.decreaseLoopCount();
    }
}

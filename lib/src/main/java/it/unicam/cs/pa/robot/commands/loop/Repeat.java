package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the REPEAT command. This class create a loop for the robot that continue to execute a block
 * of command for a specified number of times.
 * It implements the {@link LoopCommand} interface.
 *
 * @param num           the number of times the robot must repeat the next block of commands
 * @param startingIndex the command index in the list of commands
 */
public record Repeat(int num, int startingIndex) implements LoopCommand {
    /**
     * Executes the REPEAT loop command, repeating a block of commands a specified number of times.
     *
     * @param environment The environment in which the robot operates.
     * @param robot       The robot that executes the command.
     * @throws CommandException if an error occurs while executing the command.
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.pushLoopCount(num + 1, startingIndex);
        robot.decreaseLoopCount();
    }
}

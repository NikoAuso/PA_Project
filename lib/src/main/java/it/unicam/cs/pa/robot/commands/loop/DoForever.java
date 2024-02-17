package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the DO FOREVER command. This class create a loop for the robot that continue to execute a block
 * of instructions forever.
 * It implements the {@link LoopCommand} interface.
 *
 * @param startingIndex the command index in the list of commands
 */
public record DoForever(int startingIndex) implements LoopCommand {
    /**
     * Executes the DO FOREVER command on the specified environment and robot.
     *
     * @param environment The environment in which the robot operates.
     * @param robot       The robot executing the command.
     * @throws CommandException If an error occurs while executing the command.
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.increaseCurrentCommandIndex();
    }
}

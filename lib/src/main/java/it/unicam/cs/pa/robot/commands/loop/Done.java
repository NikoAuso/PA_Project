package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the DONE command. This class marks the end of a loop block.
 * It implements the {@link LoopCommand} interface.
 */
public record Done() implements LoopCommand {
    /**
     * Class representing the DONE command. This clasS marks the end of a loop block.
     *
     * @param environment The environment in which the robot operates.
     * @param robot       The robot that executes the command.
     * @throws CommandException if an error occurs while executing the command.
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.setFinishLoopIndex();
        robot.resetLoopIndex();
    }
}

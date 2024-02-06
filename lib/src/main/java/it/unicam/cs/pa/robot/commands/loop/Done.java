package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Done() implements LoopCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.setFinishLoopIndex();
        robot.resetLoopIndex();
    }
}

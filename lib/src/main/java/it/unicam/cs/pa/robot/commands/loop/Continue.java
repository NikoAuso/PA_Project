package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Continue(int duration, int startingIndex) implements LoopCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.pushLoopCount(duration, startingIndex);
        robot.setFinishLoopIndex();
        robot.resetLoopIndex();
        robot.decreaseLoopCount();
    }
}

package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Repeat(int num, int startingIndex) implements LoopCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.pushLoopCount(num + 1, startingIndex);
        robot.decreaseLoopCount();
    }
}

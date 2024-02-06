package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record DoForever(int startingIndex) implements LoopCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        robot.increaseCurrentCommandIndex();
    }
}

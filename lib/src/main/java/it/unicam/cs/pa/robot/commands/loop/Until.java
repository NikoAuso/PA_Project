package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Until(String label, int startingIndex) implements LoopCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        if (environment.getShapes().stream()
                .filter(s -> s.getLabel().equals(this.label.toUpperCase()))
                .anyMatch(s -> s.contains(robot.getPosition()))) {
            robot.pushLoopCount(1, startingIndex);
            robot.decreaseLoopCount();
        }else
            robot.increaseCurrentCommandIndex();
    }
}

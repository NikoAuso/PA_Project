package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Continue(double duration) implements MovementCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        double elapsedTime = 0;

        while (elapsedTime < duration) {
            robot.move();
            elapsedTime += 1;
        }
    }
}

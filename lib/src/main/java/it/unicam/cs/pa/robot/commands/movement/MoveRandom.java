package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record MoveRandom(Position position1, Position position2, double speed) implements MovementCommand {
    public MoveRandom {
        if (speed <= 0)
            throw new CommandException("Invalid speed value");
        if (position1.x() == 0 && position1.y() == 0 || position2.x() == 0 && position2.y() == 0)
            throw new CommandException("Invalid direction value");
    }

    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        Position direction = Position.random(position1, position2);
        robot.setSpeed(this.speed);
        robot.setDirection(direction);
        System.out.printf("MOVE RANDOM => Robot %s is moving towards position %s at speed %s%n", robot, direction, this.speed);
    }
}

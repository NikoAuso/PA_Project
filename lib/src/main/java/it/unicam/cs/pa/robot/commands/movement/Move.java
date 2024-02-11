package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Move(Position direction, double speed) implements MovementCommand {
    public Move {
        if (speed <= 0)
            throw new CommandException("Invalid speed value");
        if ((direction.x() == 0 && direction.y() == 0) || (direction.x() != 0 && direction.y() != 0))
            throw new CommandException("At most one of x or y should be non-zero.");
        if (direction.x() < -1 || direction.y() < -1 || direction.x() > 1 || direction.y() > 1)
            throw new CommandException("Invalid direction value");
    }

    @Override
    public void execute(Environment environment, Robot robot) {
        robot.setSpeed(this.speed);
        robot.setDirection(this.direction);
        System.out.printf("MOVE => Robot %s is moving towards position %s at speed %s%n", robot, this.direction, this.speed);
    }
}

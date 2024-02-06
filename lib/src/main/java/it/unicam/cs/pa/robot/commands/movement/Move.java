package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Move(Position direction, double speed) implements MovementCommand {
    public Move {
        if (speed <= 0)
            throw new CommandException("Invalid speed value");
        if (direction.x() == 0 && direction.y() == 0)
            throw new CommandException("Invalid direction value");
    }

    @Override
    public void execute(Environment environment, Robot robot) {
        robot.setSpeed(this.speed);
        robot.setDirection(this.direction);
        System.out.println("MOVE execution in direction " + this.direction + " at speed " + this.speed + " by Robot: " + robot);
    }
}

package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Move(Position direction, double speed) implements Command {
    public Move {
        if (speed < 0) {
            throw new CommandException("Invalid speed value");
        }
    }

    public Move(Position position1, Position position2, double speed){
        this(Position.random(position1, position2), speed);
    }

    @Override
    public void execute(Environment environment, Robot robot) {
        robot.setSpeed(this.speed);
        robot.setDirection(this.direction);
        robot.move();
        System.out.println("MOVE execution in direction " + this.direction + " at speed " + this.speed + " by Robot: " + robot);
    }
}

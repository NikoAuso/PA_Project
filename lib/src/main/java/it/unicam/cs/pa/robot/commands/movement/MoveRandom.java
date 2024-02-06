package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

public record MoveRandom(Position position1, Position position2, double speed) implements MovementCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        Position direction = Position.random(position1, position2);
        robot.setSpeed(this.speed);
        robot.setDirection(direction);
        System.out.println("MOVE RANDOM execution in direction " + direction + " at speed " + this.speed + " by Robot: " + robot);
    }
}

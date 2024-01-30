package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;

public class Move implements Command {
    private final Position direction;
    private final double speed;

    public Move(double xDirection, double yDirection, double speed) {
        if (xDirection < -1 || xDirection > 1 || yDirection < -1 || yDirection > 1 || speed < 0) {
            throw new IllegalArgumentException("Invalid move command parameters");
        }

        this.direction = new Position(xDirection, yDirection);
        this.speed = speed;
    }

    public Move(Position direction,  double speed) {
        if (direction.x() < -1 || direction.x() > 1 || direction.y() < -1 || direction.y() > 1 || speed < 0) {
            throw new IllegalArgumentException("Invalid move command parameters");
        }

        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Executes the MOVE command on a specified robot within a given environment.
     *
     * @param robot       The robot on which the command is executed.
     */
    @Override
    public void execute(Robot robot) {
        robot.setSpeed(this.speed);
        robot.setDirection(this.direction);
        robot.move();
        System.out.println("MOVE execution in direction " + this.direction + " at speed " + this.speed + " by Robot: " + robot);
    }
}

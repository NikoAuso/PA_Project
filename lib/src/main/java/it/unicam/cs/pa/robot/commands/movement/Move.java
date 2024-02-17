package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the MOVE command. The robot that executes this commands sets its direction and movement speed
 * to the value of the passed parameters.
 * It implements the {@link MovementCommand} interface.
 *
 * @param direction The direction in which the robot should move.
 * @param speed     The speed at which the robot should move.
 */
public record Move(Position direction, double speed) implements MovementCommand {
    /**
     * Constructs a new Move command with the specified direction and speed.
     * Check if the passed parameters are valid.
     *
     * @param direction The direction of movement.
     * @param speed     The speed of movement.
     * @throws CommandException if the speed is not positive, or if the direction is invalid.
     */
    public Move {
        if (speed <= 0)
            throw new CommandException("Invalid speed value");
        if (direction.x() == 0 || direction.y() == 0)
            throw new CommandException("At most one of x or y should be non-zero.");
        if (direction.x() < -1 || direction.y() < -1 || direction.x() > 1 || direction.y() > 1)
            throw new CommandException("Invalid direction value");
    }

    /**
     * Executes the Move command by setting the robot's direction and speed.
     *
     * @param environment the environment in which the robot operates
     * @param robot       the robot executing the command
     */
    @Override
    public void execute(Environment environment, Robot robot) {
        robot.setSpeed(this.speed);
        robot.setDirection(this.direction);
        System.out.printf("MOVE => Robot %s is moving towards position %s at speed %s%n", robot, this.direction, this.speed);
    }
}

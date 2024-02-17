package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the MOVE RANDOM command. The robot that executes this commands sets its direction within a
 * specified range of positions at a given speed.
 * It implements the {@link MovementCommand} interface.
 *
 * @param position1 The first position defining the lower bound of the movement range.
 * @param position2 The second position defining the upper bound of the movement range.
 * @param speed     The speed at which the robot should move.
 */
public record MoveRandom(Position position1, Position position2, double speed) implements MovementCommand {
    /**
     * Constructs a new MoveRandom command with the specified positions and speed.
     * Check if the passed parameters are valid.
     *
     * @param position1 The first position defining the lower bound of the movement range.
     * @param position2 The second position defining the upper bound of the movement range.
     * @param speed     The speed of movement.
     * @throws CommandException if the speed is not positive, or if the positions are invalid.
     */
    public MoveRandom {
        if (speed <= 0)
            throw new CommandException("Invalid speed value");
        if (position1.x() == 0 && position1.y() == 0 || position2.x() == 0 && position2.y() == 0)
            throw new CommandException("Invalid direction value");
    }

    /**
     * Executes the MoveRandom command by setting the robot's direction randomly within the specified range and speed.
     *
     * @param environment the environment in which the robot operates
     * @param robot       the robot executing the command
     * @throws CommandException if an error occurs during command execution
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        Position direction = Position.random(position1, position2);
        robot.setSpeed(this.speed);
        robot.setDirection(direction);
        System.out.printf("MOVE RANDOM => Robot %s is moving towards position %s at speed %s%n", robot, direction, this.speed);
    }
}

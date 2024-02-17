package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

import java.util.stream.Collectors;

/**
 * Class representing the FOLLOW command. The robot that executes this command sets its direction based on the average
 * positions of the robots that are reporting a certain label and are within a specified distance ("dist").
 * If no robot is reporting that label, the robot's direction will be set randomly within the range
 * [-dist, dist]x[-dist, dist]. Additionally, the movement speed of the robot is also set.
 * It implements the {@link MovementCommand} interface.
 *
 * @param label the label to follow
 * @param dist  the maximum distance within which robots are considered for averaging their positions
 * @param speed the movement speed of the robot
 */
public record Follow(String label, double dist, double speed) implements MovementCommand {
    /**
     * Executes the Follow command by setting the direction of the robot.
     *
     * @param environment the environment in which the robot operates
     * @param robot       the robot executing the command
     * @throws CommandException if an error occurs during command execution
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.setDirection(Position.average(environment.getSignalingRobots(label).stream()
                .map(Robot::position)
                .filter(position -> position.distanceTo(robot.position()) <= dist)
                .collect(Collectors.toList())
        ).orElse(Position.random(new Position(-dist, -dist), new Position(dist, dist))));
        robot.setSpeed(speed);
    }
}

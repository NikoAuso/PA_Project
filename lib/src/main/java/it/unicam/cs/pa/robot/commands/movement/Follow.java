package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

import java.util.stream.Collectors;

/**
 * Class representing the Follow command. The robot that executes this class sets its direction based on the average
 * of the positions of the robots that are reporting a certain "label" and that are within a certain dist distance.
 * If there is no robot reporting that label, then the robot's direction will be set randomly in the range
 * [-dist, dist]x[-dist, dist]. Furthermore, the movement speed of the robot is also set.
 */
public record Follow(String label, double dist, double speed) implements MovementCommand {
    /**
     * Executes the follow command by adjusting the direction of the robot.
     *
     * @param environment       the environment in which the robot operates
     * @param robot             the robot executing the command
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

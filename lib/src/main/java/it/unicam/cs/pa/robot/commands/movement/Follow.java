package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

import java.util.stream.Collectors;

public record Follow(String label, double dist, double speed) implements MovementCommand {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.setDirection(Position.average(environment.getSignalingRobots(label).stream()
                        .map(Robot::getPosition)
                        .filter(position -> position.distanceTo(robot.getPosition()) <= dist)
                        .collect(Collectors.toList())
                ).orElse(Position.random(new Position(-dist, -dist), new Position(dist, dist))));
        robot.setSpeed(speed);
    }
}

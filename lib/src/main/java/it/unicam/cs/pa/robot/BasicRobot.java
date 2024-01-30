package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic implementation of the Robot interface representing a robot in the simulated swarm.
 */
public class BasicRobot implements Robot {

    private Position position;      // Current position of the robot
    private Position direction;     // Direction of the robot
    private String currentLabel;    // Label associated with the current state of the robot
    private List<String> labels;    // List of all labels associated with the robot
    private double speed;           // Speed of the robot
    private boolean canMove;        // Flag that identify if the robot can move

    public BasicRobot(Position position) {
        this.position = position;
        this.currentLabel = "";
        this.labels = new ArrayList<>();
        this.direction = null;
        this.speed = 0;
        this.canMove = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Position getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Position direction) {
        this.direction = direction;
    }

    @Override
    public String getCurrentLabel() {
        return currentLabel;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void move() {
        if (!this.canMove)
            throw new RobotException("This robot can't move anymore");

        double newX = this.getPosition().x() + this.getDirection().x() * speed;
        double newY = this.getPosition().y() + this.getDirection().y() * speed;

        this.position = new Position(newX, newY);
    }

    @Override
    public void signal(String label) {
        this.currentLabel = label;
        this.labels.add(this.currentLabel);
    }

    @Override
    public void unisignal(String label) {
        if (this.currentLabel.equals(label))
            this.currentLabel = "";
        this.labels.remove(label);
    }

    @Override
    public boolean isSignaling() {
        return !this.labels.isEmpty();
    }

    @Override
    public void stop() {
        this.canMove = false;
    }
}

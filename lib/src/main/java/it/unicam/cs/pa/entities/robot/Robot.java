package it.unicam.cs.pa.entities.robot;

import it.unicam.cs.pa.entities.Position;

/**
 * Class representing a robot in the swarm.
 */
public class Robot {
    /**
     * Robot position
     */
    private final Position position;

    /**
     * Position towards which the robot have to move
     */
    private Position going_to;

    /**
     * Speed at which the robot have to move
     */
    private double speed;

    /**
     * Robot constructor from initial position value
     *
     * @param initial starting position of the robot
     */
    public Robot(Position initial) {
        this.position = initial;
        this.going_to = new Position(0, 0);
        this.speed = 0;
    }

    /**
     * Getter for robot current position in the environment
     *
     * @return position of the robot
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Getter for position towards which the robot have to move
     *
     * @return position towards which the robot have to move
     */
    public Position getGoing_to() {
        return going_to;
    }

    /**
     * Setter for position towards which the robot have to move
     *
     * @param going_to position towards which the robot have to move
     */
    public void setGoing_to(Position going_to) {
        this.going_to = going_to;
    }

    /**
     * Getter for robot speed
     *
     * @return robot speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter for robot speed
     *
     * @param speed robot speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Moves the robot towards the @var going_to position.
     */
    public void move() {
    }
}

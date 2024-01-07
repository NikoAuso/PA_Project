package it.unicam.cs.pa.entity.robot;

import it.unicam.cs.pa.entity.Position;

public class Robot {
    /**
     * Robot position
     */
    private Position position;

    /**
     * Position towards which the robot have to move
     */
    private Position going_to;

    /**
     * Speed at which the robot have to move
     */
    private double speed;

    public Robot(Position initial) {
        this.position = initial;
        this.going_to = new Position(0, 0);
        this.speed = 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getGoing_to() {
        return going_to;
    }

    public void setGoing_to(Position going_to) {
        this.going_to = going_to;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void move() {}
}

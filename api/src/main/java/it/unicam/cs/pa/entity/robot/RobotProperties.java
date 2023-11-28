package it.unicam.cs.pa.entity.robot;

public abstract class RobotProperties {
    protected double x, y;

    protected double going_to_x, going_to_y;

    protected double speed;

    public RobotProperties() {
        this.x = 0;
        this.y = 0;
        this.going_to_x = 0;
        this.going_to_y = 0;
        this.speed = 0;
    }
}

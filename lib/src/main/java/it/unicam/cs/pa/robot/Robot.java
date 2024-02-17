package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

/**
 * The Robot interface represents a robot, capable of moving, signaling or stopping signaling conditions and
 * stopping its movement.
 */
public interface Robot extends RobotMovementManagement, RobotLoopManagement {

    /**
     * Gets the current position of the robot.
     *
     * @return The current position of the robot.
     */
    Position position();

    /**
     * Gets the speed at which the robot is moving.
     *
     * @return The speed of the robot.
     */
    Position direction();

    /**
     * Gets the speed in which the robot is facing.
     *
     * @return The direction of the robot.
     */
    double speed();

    /**
     * Gets the label that the robot is currently signaling.
     *
     * @return The current label being signaled by the robot.
     */
    String currentLabel();

    /**
     * Sets the direction in which the robot should move.
     *
     * @param direction The new direction for the robot.
     */
    void setDirection(Position direction);

    /**
     * Sets the speed at which the robot should move.
     *
     * @param speed The speed of the robot.
     */
    void setSpeed(double speed);
}

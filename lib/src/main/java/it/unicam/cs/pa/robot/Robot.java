package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

/**
 * The Robot interface represents a robot capable of executing commands and interacting with its environment.
 */
public interface Robot extends RobotCommandManagement {

    /**
     * Gets the current position of the robot.
     *
     * @return The current position of the robot.
     */
    Position position();

    /**
     * Gets the direction in which the robot is facing.
     *
     * @return The direction of the robot.
     */
    Position direction();

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

    /**
     * Checks if the robot is able to move.
     *
     * @return True if the robot can move, false otherwise.
     */
    boolean canMove();

    /**
     * Moves the robot in the current direction at the specified speed.
     */
    void move();

    /**
     * Signals a condition with the specified label.
     *
     * @param label The label of the condition to be signaled.
     */
    void signal(String label);

    /**
     * Stops signaling a condition with the specified label.
     *
     * @param label The label of the condition to stop signaling.
     */
    void unsignal(String label);

    /**
     * Checks if the robot is currently signaling a condition with the specified label.
     *
     * @param label The label of the condition to check.
     * @return True if the robot is signaling the condition, false otherwise.
     */
    boolean isSignaling(String label);

    /**
     * Stops the robot from moving.
     */
    void stop();
}

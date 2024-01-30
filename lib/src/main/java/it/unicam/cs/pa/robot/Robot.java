package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

/**
 * Interface representing a robot in the simulated swarm.
 */
public interface Robot {

    /**
     * Gets the current position of the robot.
     *
     * @return the current position of the robot
     */
    Position getPosition();

    /**
     * Gets the direction of the robot.
     *
     * @return the direction of the robot
     */
    Position getDirection();

    /**
     * Sets the direction of the robot.
     *
     * @param direction the direction of the robot
     */
    void setDirection(Position direction);

    /**
     * Gets the label associated with the current state of the robot.
     *
     * @return the label associated with the current state of the robot
     */
    String getCurrentLabel();

    /**
     * Gets the current speed of the robot.
     *
     * @return the current speed of the robot
     */
    double getSpeed();

    /**
     * Set the current speed of the robot.
     *
     * @param speed the new speed to set
     */
    void setSpeed(double speed);

    /**
     * Moves the robot based on its direction and speed.
     */
    void move();

    /**
     * Signals a particular condition by associating a label with the robot's state.
     *
     * @param label the label to signal
     */
    void signal(String label);

    /**
     * Stops signaling the current condition.
     *
     * @param label The label to un-signal.
     */
    void unisignal(String label);

    /**
     * Checks if the robot is currently signaling a condition.
     *
     * @return True if the robot is signaling, false otherwise.
     */
    boolean isSignaling();

    /**
     * Stops the robot's movement.
     */
    void stop();
}

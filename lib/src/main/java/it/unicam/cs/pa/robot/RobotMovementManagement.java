package it.unicam.cs.pa.robot;

/**
 * The RobotMovementManagement interface defines methods for managing robot movement commands.
 */
public interface RobotMovementManagement {
    /**
     * Checks if the robot is able to move.
     *
     * @return True if the robot can move, false otherwise.
     */
    boolean canMove();

    /**
     * Moves the robot based on its current direction at the specified speed.
     */
    void move();

    /**
     * Signals a condition with the specified label.
     *
     * @param label The label of the condition to be signaled.
     */
    void signal(String label);

    /**
     * Stops signaling the condition of the specified label.
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

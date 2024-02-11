package it.unicam.cs.pa.robot;

/**
 * The RobotCommandManagement interface defines methods for managing robot commands and loop execution.
 */
public interface RobotCommandManagement {

    /**
     * Gets the current index of the command being executed by the robot.
     *
     * @return The current command index.
     */
    int currentCommandIndex();

    /**
     * Sets the index indicating the end of a loop execution.
     */
    void setFinishLoopIndex();

    /**
     * Increases the current command index by one.
     */
    void increaseCurrentCommandIndex();

    /**
     * Decreases the current command index by one.
     */
    void decreaseCurrentCommandIndex();

    /**
     * Pushes the starting index of a loop to the loop stack.
     *
     * @param index The starting index of the loop.
     */
    void pushStartingLoopIndex(int index);

    /**
     * Pushes the loop count and the index where the loop starts to the loop stack.
     *
     * @param count The remaining iterations of the loop.
     * @param index The index where the loop starts.
     */
    void pushLoopCount(int count, int index);

    /**
     * Decreases the loop count by one.
     */
    void decreaseLoopCount();

    /**
     * Resets the loop index to the starting index of the current loop iteration.
     */
    void resetLoopIndex();
}

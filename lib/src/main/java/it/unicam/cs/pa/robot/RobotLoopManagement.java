package it.unicam.cs.pa.robot;

/**
 * The RobotLoopManagement interface defines methods for managing robot loop commands and execution.
 * This interface allows to manage robot commands and also gives the possibility of managing nested loops.
 */
public interface RobotLoopManagement {

    /**
     * Gets the current index of the command being executed by the robot.
     *
     * @return The current command index.
     */
    int currentCommandIndex();

    /**
     * Increases the current command index by one.
     */
    void increaseCurrentCommandIndex();

    /**
     * Sets the index indicating the end of a loop execution.
     */
    void setFinishLoopIndex();

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

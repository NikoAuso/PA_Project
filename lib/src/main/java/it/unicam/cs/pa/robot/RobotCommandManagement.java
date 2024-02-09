package it.unicam.cs.pa.robot;

public interface RobotCommandManagement {
    int getCurrentCommandIndex();

    void setFinishLoopIndex();

    void increaseCurrentCommandIndex();

    void pushStartingLoopIndex(int index);

    void pushLoopCount(int count, int index);

    void decreaseLoopCount();

    void resetLoopIndex();
}

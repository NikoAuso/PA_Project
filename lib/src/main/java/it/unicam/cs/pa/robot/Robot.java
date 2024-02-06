package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

public interface Robot {

    Position getPosition();

    Position getDirection();

    int getCurrentCommandIndex();

    void setFinishLoopIndex();

    boolean canMove();

    void setDirection(Position direction);

    void setSpeed(double speed);

    void increaseCurrentCommandIndex();

    void pushStartingLoopIndex(int index);

    void pushLoopCount(int count, int index);

    void decreaseLoopCount();

    void resetLoopIndex();

    void move();

    void signal(String label);

    void unisignal(String label);

    boolean isSignaling(String label);

    void stop();
}

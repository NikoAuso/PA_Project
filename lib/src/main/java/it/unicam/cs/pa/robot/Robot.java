package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

public interface Robot extends RobotCommandManagement {
    Position position();

    Position direction();

    String currentLabel();

    void setDirection(Position direction);

    void setSpeed(double speed);

    boolean canMove();

    void move();

    void signal(String label);

    void unsignal(String label);

    boolean isSignaling(String label);

    void stop();
}

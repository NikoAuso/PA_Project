package it.unicam.cs.pa.entity.robot;

public interface Robot {
    void move(double targetX, double targetY, double speed);

    void moveRandom(double minX, double maxX, double minY, double maxY, double speed);

    void signal(String label);

    void unsignal(String label);

    void follow(String label, double distance, double speed);

    void continueMoving(double seconds);

    void stop();
    // Altre azioni e comportamenti dei robot
}

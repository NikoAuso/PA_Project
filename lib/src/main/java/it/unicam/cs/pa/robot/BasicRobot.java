package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A basic implementation representing a robot in the swarm.
 */
public class BasicRobot implements Robot {
    private Position position;
    private Position direction;
    private String currentLabel;
    private final List<String> signalingLabels;
    private double speed;
    private boolean canMove;

    private int currentCommandIndex;
    private final Deque<Integer> startingLoopIndices;
    private int finishLoopIndex;
    private final Deque<Integer> loopCounts;

    /**
     * Constructs a new BasicRobot with the given initial position.
     *
     * @param position The initial position of the robot.
     */
    public BasicRobot(Position position) {
        this.position = position;
        this.currentLabel = "";
        this.signalingLabels = new ArrayList<>();
        this.direction = new Position(0, 0);
        this.speed = 0;
        this.canMove = true;
        this.currentCommandIndex = 0;
        this.startingLoopIndices = new ArrayDeque<>();
        this.loopCounts = new ArrayDeque<>();
    }


    /* ______________________________________________
     * Methods for "Robot" interface
     * ______________________________________________
     */
    @Override
    public Position position() {
        return this.position;
    }

    @Override
    public Position direction() {
        return this.direction;
    }

    @Override
    public double speed() {
        return this.speed;
    }

    @Override
    public String currentLabel() {
        return currentLabel;
    }

    @Override
    public void setDirection(Position direction) {
        double deltaX = direction.x() - this.position.x();
        double deltaY = direction.y() - this.position.y();

        double module = Math.hypot(deltaX, deltaY);
        this.direction = new Position(deltaX / module, deltaY / module);
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public boolean canMove() {
        return this.canMove;
    }

    @Override
    public void move() {
        double newX = this.position().x() + this.direction().x() * speed;
        double newY = this.position().y() + this.direction().y() * speed;

        if (Double.isFinite(newX) && Double.isFinite(newY))
            this.position = new Position(newX, newY);
    }

    @Override
    public void signal(String label) {
        this.currentLabel = label;
        this.signalingLabels.add(this.currentLabel);
    }

    @Override
    public void unsignal(String label) {
        if (this.currentLabel.equals(label))
            this.currentLabel = "";
        this.signalingLabels.remove(label);
    }

    @Override
    public boolean isSignaling(String label) {
        return this.signalingLabels.contains(label);
    }

    @Override
    public void stop() {
        this.canMove = false;
    }


    /* ________________________________________________
     * Methods for "RobotCommandManagement" interface
     * ________________________________________________
     */
    @Override
    public int currentCommandIndex() {
        return this.currentCommandIndex;
    }

    @Override
    public void increaseCurrentCommandIndex() {
        this.currentCommandIndex++;
    }

    @Override
    public void setFinishLoopIndex() {
        this.finishLoopIndex = currentCommandIndex + 1;
    }

    @Override
    public void pushStartingLoopIndex(int index) {
        if (!this.startingLoopIndices.contains(index))
            this.startingLoopIndices.push(index);
    }

    @Override
    public void pushLoopCount(int count, int index) {
        if (!this.startingLoopIndices.isEmpty() && this.startingLoopIndices.contains(index))
            if(this.loopCounts.size() < this.startingLoopIndices.size())
                this.loopCounts.push(count);
    }

    @Override
    public void decreaseLoopCount() {
        Integer tmpCount = this.loopCounts.pollLast();
        if (tmpCount != null) {
            if (tmpCount == 0) {
                this.currentCommandIndex = finishLoopIndex;
            } else {
                this.loopCounts.offerLast(tmpCount - 1);
                this.increaseCurrentCommandIndex();
            }
        }
    }

    @Override
    public void resetLoopIndex() {
        this.currentCommandIndex = startingLoopIndices.pop();
    }
}

package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A basic implementation of the Robot interface representing a robot in the simulated swarm.
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

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Position getDirection() {
        return this.direction;
    }

    @Override
    public int getCurrentCommandIndex() {
        return this.currentCommandIndex;
    }

    @Override
    public boolean canMove() {
        return this.canMove;
    }

    @Override
    public void setFinishLoopIndex() {
        this.finishLoopIndex = currentCommandIndex + 1;
    }

    @Override
    public void setDirection(Position direction) {
        double module = Math.hypot(direction.x(), direction.y());
        this.direction = new Position(direction.x() / module, direction.y() / module);
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void increaseCurrentCommandIndex() {
        this.currentCommandIndex++;
    }

    @Override
    public void pushStartingLoopIndex(int index) {
        this.startingLoopIndices.push(index);
    }

    @Override
    public void pushLoopCount(int count, int index) {
        if (this.startingLoopIndices.isEmpty() || this.startingLoopIndices.peekFirst() != index)
            this.loopCounts.push(count);
    }

    @Override
    public void decreaseLoopCount() {
        int tmpCount = this.loopCounts.pop();
        if (tmpCount == 0)
            this.currentCommandIndex = finishLoopIndex;
        else {
            this.loopCounts.push(tmpCount - 1);
            this.increaseCurrentCommandIndex();
        }
    }

    @Override
    public void resetLoopIndex() {
        this.currentCommandIndex = startingLoopIndices.pop();
    }

    @Override
    public void move() {
        if (!this.canMove)
            throw new RobotException("This robot can't move anymore");

        double newX = this.getPosition().x() + this.getDirection().x() * speed;
        double newY = this.getPosition().y() + this.getDirection().y() * speed;

        if (Double.isFinite(newX) && Double.isFinite(newY))
            this.position = new Position(newX, newY);
    }

    @Override
    public void signal(String label) {
        this.currentLabel = label;
        this.signalingLabels.add(this.currentLabel);
    }

    @Override
    public void unisignal(String label) {
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
}

package it.unicam.cs.pa.environment;

import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.BasicRobot;
import it.unicam.cs.pa.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an environment consisting of robots and shapes.
 */
public class Environment {
    private final List<Robot> robots;
    private final List<Shape> shapes;

    /**
     * Constructs an environment with the specified lists of robots and shapes.
     *
     * @param robots The list of robots in the environment.
     * @param shapes The list of shapes in the environment.
     */
    public Environment(List<Robot> robots, List<Shape> shapes) {
        this.robots = robots;
        this.shapes = shapes;
    }

    /**
     * Constructs an empty environment.
     */
    public Environment() {
        this.robots = new ArrayList<>();
        this.shapes = new ArrayList<>();
    }

    /**
     * Returns the list of robots in the environment.
     *
     * @return The list of robots.
     */
    public List<Robot> getRobots() {
        return this.robots;
    }

    /**
     * Returns the list of shapes in the environment.
     *
     * @return The list of shapes.
     */
    public List<Shape> getShapes() {
        return this.shapes;
    }

    /**
     * Returns the list of robots in the environment that are signaling the specified label.
     *
     * @param label The label to filter the signaling robots.
     * @return The list of signaling robots.
     */
    public List<Robot> getSignalingRobots(String label) {
        return this.robots.stream().filter(r -> r.isSignaling(label)).collect(Collectors.toList());
    }

    /**
     * Generates and adds a specified number of random robots to the environment within a specified bound.
     *
     * @param n     The number of robots to generate.
     * @param bound The bound for random positions.
     */
    public void generateRandomRobots(int n, int bound) {
        for (int i = 0; i < n; i++) {
            this.robots.add(new BasicRobot(Position.random(bound)));
        }
    }
}

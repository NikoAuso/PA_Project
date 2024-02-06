package it.unicam.cs.pa.environment;

import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.BasicRobot;
import it.unicam.cs.pa.robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Environment  {
    private final List<Robot> robots;
    private final List<Shape> shapes;

    public Environment(List<Robot> robots, List<Shape> shapes) {
        this.robots = robots;
        this.shapes = shapes;
    }

    public Environment(){
        this.robots = new ArrayList<>();
        this.shapes = new ArrayList<>();
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    public List<Shape> getShapes() {
        return this.shapes;
    }

    public List<Robot> getSignalingRobots(String label) {
        return this.robots.stream()
                .filter(r -> r.isSignaling(label))
                .collect(Collectors.toList());
    }

    public void generateRandomRobots(int n, int bound){
        for (int i = 0; i < n; i++){
            this.robots.add(new BasicRobot(Position.random(bound)));
        }
    }
}

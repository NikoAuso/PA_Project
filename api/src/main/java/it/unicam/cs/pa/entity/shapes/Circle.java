package it.unicam.cs.pa.entity.shapes;

import it.unicam.cs.pa.entity.Position;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Circle implements Shape {
    private final Position center;

    private final double radius;

    public Circle(Position center, double radius) {
        if (radius > 0) {
            this.center = center;
            this.radius = radius;
        }else
            throw new RuntimeException("Radius of the circle must be a positive value.");
    }

    @Override
    public Position getCenter() {
        return this.center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isContained(Position toCheckposition) {
        return false;
    }

    @Override
    public List<Position> generateRandomPositions(int total) {
        if (!(total > 0))
            throw new RuntimeException("The number of random position to generate must be positive");
        return IntStream.range(0, total)
                .mapToObj(i -> {
                    double r = new Random().nextInt(50);
                    return new Position(new Random().nextInt(100), new Random().nextInt(100));
                }).collect(Collectors.toList());
    }
}
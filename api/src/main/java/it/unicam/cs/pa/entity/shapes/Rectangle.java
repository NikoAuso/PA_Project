package it.unicam.cs.pa.entity.shapes;

import it.unicam.cs.pa.entity.Position;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Rectangle implements Shape {
    private final Position center;

    private final double height;

    private final double width;

    public Rectangle(Position center, double height, double width) {
        if (height > 0 && width > 0) {
            this.center = center;
            this.height = height;
            this.width = width;
        }else
            throw new RuntimeException("Height and width of the rectangle must be a positive value.");

    }

    @Override
    public Position getCenter() {
        return center;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
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
                    double h = new Random().nextInt(50);
                    double w = new Random().nextInt(50);
                    return new Position(new Random().nextInt(100), new Random().nextInt(100));
                }).collect(Collectors.toList());
    }
}

package it.unicam.cs.pa.entity;

import java.util.random.RandomGenerator;

public final class Position {
    private final double x;

    private final double y;

    public Position(double x, double y) {
        if (!(x > 0 && y > 0))
            throw new RuntimeException("The position value must be positive");
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    public static Position random() {
        return new Position(RandomGenerator.getDefault().nextDouble(), RandomGenerator.getDefault().nextDouble());
    }
}

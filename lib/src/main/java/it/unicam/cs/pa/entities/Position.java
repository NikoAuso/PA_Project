package it.unicam.cs.pa.entities;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Describes the coordinates of a position in the environment
 *
 * @param x X coordinate of the position
 * @param y Y coordinate of the position
 */
public record Position(double x, double y) {
    /**
     * Position constructor from values
     *
     * @param x X coordinate of the position
     * @param y Y coordinate of the position
     */
    public Position {
        if (!(x > 0 && y > 0))
            throw new PositionException("The position values must be positive");
    }

    /**
     * Generate random position
     *
     * @return random position
     */
    public static Position random() {
        Random random = new Random();
        return new Position(
                random.nextDouble(),
                random.nextDouble()
        );
    }

    /**
     * Check if two position are equals, based on the coordinates of both
     *
     * @param obj the other position to check
     * @return true if both the position sre the same, false otherwise
     */
    public boolean equals(Position obj) {
        return Double.compare(this.x(), obj.x()) == 0 && Double.compare(this.y(), obj.y()) == 0;
    }

    /**
     * Calculate the distance between two position
     *
     * @param p the other position
     * @return distance between two position
     */
    public double distanceTo(Position p) {
        return Math.hypot(p.x - this.x(), p.y - this.y());
    }

    /**
     * Generate a list of random position
     *
     * @param total amount of position to be created
     * @return a list of random position
     */
    public static List<Position> generateRandomPositions(int total) throws PositionException{
        if (!(total > 0))
            throw new PositionException("The amount of random position to generate must be positive");
        return IntStream.range(0, total)
                .mapToObj(i -> random())
                .collect(Collectors.toList());
    }

}

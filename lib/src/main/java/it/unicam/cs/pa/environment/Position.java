package it.unicam.cs.pa.environment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * A record representing a 2D position with x and y coordinates.
 * The class provides methods for creating a random position,
 * checking equality, and calculating the distance between two positions.
 */
public record Position(double x, double y) {
    private static final int MAX_DIGITS = 15;

    /**
     * Initializes a Position with the specified x and y coordinates.
     * Throws a PositionException if either x or y is not a finite value.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @throws PositionException if either x or y is not a finite value.
     */
    public Position {
        validateCoordinate(x);
        validateCoordinate(y);
    }

    /**
     * Generates a random Position with coordinates within a specified range.
     *
     * @return A randomly generated Position object.
     */
    public static Position random(int bound) {
        return new Position(randomCoordinate(bound), randomCoordinate(bound));
    }

    public static Position random(Position min, Position max) {
        if (min.distanceTo(max) <= 0) {
            throw new PositionException("The positions provided must be distant from each other.");
        }

        Random random = new Random();
        return new Position(
                random.nextDouble(Math.min(min.x(), max.x()), Math.max(min.x(), max.x())),
                random.nextDouble(Math.min(min.y(), max.y()), Math.max(min.y(), max.y()))
        );
    }

    /**
     * Generates a random coordinate within a specified range.
     *
     * @return A randomly generated coordinate value.
     */
    private static double randomCoordinate(int bound) {
        Random random = new Random();

        double randomValue = (random.nextDouble() * bound * 2) - bound;
        randomValue = BigDecimal.valueOf(randomValue).setScale(MAX_DIGITS, RoundingMode.HALF_UP).doubleValue();

        return randomValue;
    }

    /**
     * Compares the position with another Position for equality.
     *
     * @param pos The position to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    public boolean equals(Position pos) {
        return Double.compare(this.x(), pos.x()) == 0 && Double.compare(this.y(), pos.y()) == 0;
    }

    /**
     * Calculates the Euclidean distance between this position and another position.
     *
     * @param p The position to calculate the distance to.
     * @return The distance between the two positions.
     */
    public double distanceTo(Position p) {
        return Math.hypot(p.x - this.x(), p.y - this.y());
    }

    /**
     * Validates a coordinate value to ensure it is finite and within a specified range.
     *
     * @param coordinate The coordinate value to be validated.
     * @throws PositionException if the coordinate value is not finite or falls outside the valid range.
     */
    private void validateCoordinate(double coordinate) {
        double maxValue = Math.pow(10, MAX_DIGITS);

        // Check if the coordinate is finite
        if (!Double.isFinite(coordinate)) throw new PositionException("The position values must be finite value");

        // Check if the coordinate is within the specified range
        if (coordinate < -maxValue || coordinate > maxValue) {
            throw new PositionException(String.format("The coordinate value must be in the range [-10^%d, 10^%d]", MAX_DIGITS, MAX_DIGITS));
        }
    }
}

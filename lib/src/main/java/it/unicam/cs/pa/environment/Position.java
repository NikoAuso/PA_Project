package it.unicam.cs.pa.environment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A record representing a 2D position with x and y coordinates.
 * The class provides methods for creating a random position, checking equality, and calculating the distance
 * between two positions.
 */
public record Position(double x, double y) {

    /**
     * Maximum number of digits for rounding coordinate values.
     */
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
     * @param bound The upper bound for the range of random coordinates.
     * @return A randomly generated Position object.
     */
    public static Position random(int bound) {
        return new Position(randomCoordinate(bound), randomCoordinate(bound));
    }

    /**
     * Generates a random Position with coordinates within a specified range.
     * The generated position is constrained within the range defined by min and max positions.
     *
     * @param min The minimum position defining the lower bounds for randomization.
     * @param max The maximum position defining the upper bounds for randomization.
     * @return A randomly generated Position object within the specified range.
     * @throws PositionException if min and max positions are too close to each other.
     */
    public static Position random(Position min, Position max) {
        if (min.distanceTo(max) <= 0) {
            throw new PositionException("The positions provided must be distant from each other.");
        }

        double EPSILON = 1e-10;
        double deltaX = min.x() == max.x() ? EPSILON : 0;
        double deltaY = min.y() == max.y() ? EPSILON : 0;

        return new Position(
                ThreadLocalRandom.current().nextDouble(Math.min(min.x(), max.x()) - deltaX, Math.max(min.x(), max.x()) + deltaY),
                ThreadLocalRandom.current().nextDouble(Math.min(min.y(), max.y()) - deltaX, Math.max(min.y(), max.y()) + deltaY)
        );
    }

    /**
     * Generates a random coordinate within a specified range.
     *
     * @param bound The upper bound for the range of random coordinates.
     * @return A randomly generated coordinate value.
     */
    private static double randomCoordinate(int bound) {
        double randomValue = (ThreadLocalRandom.current().nextDouble() * bound * 2) - bound;
        randomValue = BigDecimal.valueOf(randomValue).setScale(MAX_DIGITS, RoundingMode.HALF_UP).doubleValue();

        return randomValue;
    }

    /**
     * Computes the average position from a list of positions.
     *
     * @param positions The list of positions to compute the average from.
     * @return An Optional containing the average position if positions is not empty, empty otherwise.
     */
    public static Optional<Position> average(List<Position> positions) {
        Optional<Position> a = positions.parallelStream()
                .reduce((accP, p) -> new Position(accP.x() + p.x(), accP.y() + p.y()));
        return a.map(position -> new Position(position.x() / positions.size(), position.y() / positions.size()));
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
    static void validateCoordinate(double coordinate) {
        double maxValue = Math.pow(10, MAX_DIGITS);

        if (!Double.isFinite(coordinate)) throw new PositionException("The position values must be finite value");

        if (coordinate < -maxValue || coordinate > maxValue) {
            throw new PositionException(String.format("The coordinate value must be within the range [-10^-%d, 10^%d].", MAX_DIGITS, MAX_DIGITS));

        }
    }
}

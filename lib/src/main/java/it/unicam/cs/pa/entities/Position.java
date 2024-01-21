package it.unicam.cs.pa.entities;

/**
 * A record representing a 2D position with x and y coordinates. The class provides methods for creating
 * a random position, checking equality, and calculating the distance between two positions.
 */
public record Position(double x, double y) {
    /**
     * Initializes a Position with the specified x and y coordinates.
     * Throws a PositionException if either x or y is not a finite value.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @throws PositionException if either x or y is not a finite value.
     */
    public Position {
        if (!Double.isFinite(x) || !Double.isFinite(y))
            throw new PositionException("The position values must be finite value");
    }

    /**
     * Generates a random Position with x and y coordinates between 0 (inclusive) and 1 (exclusive).
     *
     * @return A randomly generated Position.
     */
    public static Position random() {
        return new Position(
                Math.random() * Double.MAX_VALUE,
                Math.random() * Double.MAX_VALUE
        );
    }

    /**
     * Compares the position with another Position for equality.
     *
     * @param obj The position to compare with.
     * @return true if the positions are equal, false otherwise.
     */
    public boolean equals(Position obj) {
        return Double.compare(this.x(), obj.x()) == 0 && Double.compare(this.y(), obj.y()) == 0;
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
}

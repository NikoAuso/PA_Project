package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.unicam.cs.pa.entities.Position.random;

/**
 * Interface for shapes in the environment.
 */
public interface Shape{
    /**
     * Check if a given position is contained in the shape.
     *
     * @param toCheckPosition position to check.
     * @return true if the position is contained, false otherwise.
     */
    boolean contains(Position toCheckPosition);

    /**
     * Check if two shapes are equals.
     *
     * @param s the other shape to check.
     * @return true if both are equals, false otherwise.
     */
    @Override
    boolean equals(Object s);

    /**
     * Create the hashCode for the rectangular shape.
     *
     * @return hashCode of the rectangular shape.
     */
    @Override
    int hashCode();

    /**
     * Get the shape properties as a string.
     *
     * @return shape properties description.
     */
    @Override
    String toString();

    /**
     * Randomly generates a shape (circle or rectangle).
     *
     * @return a randomly generated shape.
     */
    static Shape generateRandomShape() {
        Random random = new Random();
        Position position = random();
        if (random.nextBoolean()) {
            double radius = random.nextDouble() * 10;
            return new Circle(position, radius);
        } else {
            double width = random.nextDouble() * 10;
            double height = random.nextDouble() * 10;
            return new Rectangle(position, height, width);
        }
    }

    /**
     * Generate a list of random shape to spawn in the environment.
     *
     * @param total the number of shape to spawn.
     * @return list of random shape.
     */
    static List<Shape> generateRandomShape(int total){
        if (!(total > 0))
            throw new RuntimeException("The number of random position to generate must be positive");
        return IntStream.range(0, total)
                .mapToObj(i -> generateRandomShape())
                .collect(Collectors.toList());
    }
}

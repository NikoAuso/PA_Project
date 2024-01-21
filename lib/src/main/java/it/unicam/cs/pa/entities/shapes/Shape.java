package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;

/**
 * An interface representing a geometric shape in a 2D space. Classes that implement it should provide
 * methods for containment check, equality comparison, and a string representation of the shape.
 */
public interface Shape{
    /**
     * Checks if the given position is contained within the shape.
     *
     * @param toCheckPosition The position to check for containment.
     * @return true if the position is inside the shape, false otherwise.
     */
    boolean contains(Position toCheckPosition);

    /**
     * Compares the shape with another Shape for equality.
     *
     * @param shape The shape to compare with.
     * @return true if the shapes are equal, false otherwise.
     */
    boolean equals(Shape shape);

    /**
     * Returns a string representation of the shape.
     *
     * @return String representation of the shape.
     */
    String toString();

//    /**
//     * Randomly generates a shape (circle or rectangle).
//     *
//     * @return a randomly generated shape.
//     */
//    static Shape generateRandomShape() {
//        Random random = new Random();
//        Position position = random();
//        if (random.nextBoolean()) {
//            double radius = random.nextDouble() * 10;
//            return new Circle(position, radius);
//        } else {
//            double width = random.nextDouble() * 10;
//            double height = random.nextDouble() * 10;
//            return new Rectangle(position, height, width);
//        }
//    }
//
//    /**
//     * Generate a list of random shape to spawn in the environment.
//     *
//     * @param total the number of shape to spawn.
//     * @return list of random shape.
//     */
//    static List<Shape> generateRandomShape(int total){
//        if (!(total > 0))
//            throw new RuntimeException("The number of random position to generate must be positive");
//        return IntStream.range(0, total)
//                .mapToObj(i -> generateRandomShape())
//                .collect(Collectors.toList());
//    }
}

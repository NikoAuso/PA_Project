package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;

/**
 * An interface representing a geometric shape in a 2D space. Classes that implement it should provide
 * methods for containment check, equality comparison, and a string representation of the shape.
 */
public interface Shape{
    /**
     * Get the type of the shape.
     *
     * @return type of the shape
     */
    ShapeType getType();

    /**
     * Get the label of the shape.
     *
     * @return label of the shape
     */
    String getLabel();

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
}

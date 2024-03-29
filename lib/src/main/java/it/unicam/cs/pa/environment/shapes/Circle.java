package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;

/**
 * A record representing a circle in a 2D space. The circle is defined by its center and radius.
 * The class implements the Shape interface, providing methods for containment check, equality comparison,
 * and a string representation of the circle.
 */
public record Circle(Position center, double radius, String label) implements Shape {
    /**
     * Constructs a Circle with the specified center and radius. Throws a ShapeException if
     * the radius is not a positive value.
     *
     * @param center The center position of the circle.
     * @param radius The radius of the circle.
     * @param label The label of the circle.
     * @throws ShapeException if the radius is not a positive value.
     */
    public Circle(Position center, double radius, String label) {
        if (radius > 0) {
            this.center = center;
            this.radius = radius;
            this.label = label;
        } else
            throw new ShapeException("Radius of the circle must be a positive value.");
    }

    @Override
    public ShapeType getType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public boolean contains(Position toCheckPosition) {
        double distance = this.center().distanceTo(toCheckPosition);
        return distance <= this.radius();
    }
}

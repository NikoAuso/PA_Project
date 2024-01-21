package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;

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
    public boolean contains(Position toCheckPosition) {
        double distance = this.center().distanceTo(toCheckPosition);
        return distance <= this.radius();
    }

    @Override
    public boolean equals(Shape s) {
        if (s == this) {
            return true;
        } else if (!(s instanceof Circle r)) {
            return false;
        } else {
            return this.center().x() == r.center().x() &&
                    this.center().y() == r.center().y() &&
                    this.radius() == r.radius();
        }
    }

    @Override
    public String toString() {
        return "Shape: " + this.getClass().getSimpleName() + "->[" +
                "X=" + this.center().x() + ", " +
                "Y=" + this.center().y() + ", " +
                "R=" + this.radius() + ", " +
                "Label=" + this.label() + "]";

    }
}

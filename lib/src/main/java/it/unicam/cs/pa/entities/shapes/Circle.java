package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;

/**
 * Describe the circular shape in the environment
 *
 * @param center center of the circular shape
 * @param radius radius of the circular shape
 */
public record Circle(Position center, double radius) implements Shape {
    /**
     * Circle constructor from values
     *
     * @param center the center coordinate of the shape
     * @param radius the radius of the circle
     */
    public Circle(Position center, double radius) {
        if (radius > 0) {
            this.center = center;
            this.radius = radius;
        } else
            throw new ShapeException("Radius of the circle must be a positive value.");
    }

    @Override
    public boolean contains(Position toCheckPosition) {
        double distance = this.center().distanceTo(toCheckPosition);
        return distance <= this.radius();
    }

    @Override
    public boolean equals(Object s) {
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
    public int hashCode() {
        long bits = Double.doubleToLongBits(this.center().x());
        bits += Double.doubleToLongBits(this.center().y()) * 37L;
        bits += Double.doubleToLongBits(this.radius()) * 43L;
        return (int) bits ^ (int) (bits >> 32);
    }

    @Override
    public String toString() {
        String description = this.getClass().getName();
        return description +
                "Shape: Circle->[X=" + this.center().x() + "," +
                "Y=" + this.center().y() + "," +
                "R=" + this.radius() + "]";

    }
}

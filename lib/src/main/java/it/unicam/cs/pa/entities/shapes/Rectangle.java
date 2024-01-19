package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;

/**
 * Describe the rectangular shape in the environment
 *
 * @param center center coordinate of the shape
 * @param height height of the rectangular shape
 * @param width  width of the rectangular shape
 */
public record Rectangle(Position center, double height, double width) implements Shape {
    /**
     * Rectangle constructor from values
     *
     * @param center center coordinate of the shape
     * @param height height of the rectangle
     * @param width  width of the rectangle
     */
    public Rectangle(Position center, double height, double width) {
        if (height > 0 && width > 0) {
            this.center = center;
            this.height = height;
            this.width = width;
        } else
            throw new ShapeException("Height and width of the rectangle must be a positive value.");
    }


    @Override
    public boolean contains(Position toCheckPosition) {
        double distanceX = Math.abs(this.center().x() - toCheckPosition.x());
        double distanceY = Math.abs(this.center().y() - toCheckPosition.y());

        double halfWidth = this.width() / 2.0;
        double halfHeight = this.height() / 2.0;

        return distanceX <= halfWidth && distanceY <= halfHeight;
    }

    @Override
    public boolean equals(Object s) {
        if (s == this) {
            return true;
        } else if (!(s instanceof Rectangle r)) {
            return false;
        } else {
            return this.center().x() == r.center().x() &&
                    this.center().y() == r.center().y() &&
                    this.width() == r.width() &&
                    this.height() == r.height();
        }
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(this.center().x());
        bits += Double.doubleToLongBits(this.center().y()) * 37L;
        bits += Double.doubleToLongBits(this.width()) * 43L;
        bits += Double.doubleToLongBits(this.height()) * 47L;
        return (int) bits ^ (int) (bits >> 32);
    }

    @Override
    public String toString() {
        String description = this.getClass().getName();
        return description +
                "Shape: Rectangle->[X=" + this.center().x() + "," +
                "Y=" + this.center().y() + "," +
                "W=" + this.width() + "," +
                "H=" + this.height() + "]";
    }
}

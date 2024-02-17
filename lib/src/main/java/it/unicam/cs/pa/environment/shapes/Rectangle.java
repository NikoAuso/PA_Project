package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;

/**
 * A record representing a rectangle in a 2D space. The rectangle is defined by its center, height, and width.
 * The class implements the Shape interface, providing methods for containment check, equality comparison,
 * hash code generation, and a string representation of the rectangle.
 */
public record Rectangle(Position center, double width, double height, String label) implements Shape {
    /**
     * Constructs a Rectangle with the specified center, height, and width. Throws a ShapeException if
     * the height or width is not a positive value.
     *
     * @param center The center position of the rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param label The label of the rectangle.
     * @throws ShapeException if height or width is not a positive value.
     */
    public Rectangle(Position center, double width, double height, String label) {
        if (height > 0 && width > 0) {
            this.center = center;
            this.height = height;
            this.width = width;
            this.label = label;
        } else
            throw new ShapeException("Height and width of the rectangle must be a positive value.");
    }

    @Override
    public ShapeType getType() {
        return ShapeType.RECTANGLE;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public boolean contains(Position toCheckPosition) {
        double distanceX = Math.abs(this.center().x() - toCheckPosition.x());
        double distanceY = Math.abs(this.center().y() - toCheckPosition.y());

        double halfWidth = this.width() / 2.0;
        double halfHeight = this.height() / 2.0;

        return distanceX <= halfWidth && distanceY <= halfHeight;
    }
}

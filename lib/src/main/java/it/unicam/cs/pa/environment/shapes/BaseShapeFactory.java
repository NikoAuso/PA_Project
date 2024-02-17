package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;

/**
 * A factory class for creating instances of shapes.
 * This implementation provides methods for creating circles and rectangles.
 */
public class BaseShapeFactory implements ShapeFactory {
    @Override
    public Shape createShape(String label, ShapeType shapeType, double[] args) {
        Position center = new Position(args[0], args[1]);
        return switch (shapeType) {
            case CIRCLE -> createCircle(center, args, label);
            case RECTANGLE -> createRectangle(center, args, label);
        };
    }

    /**
     * Creates a circle with the provided center, radius, and label.
     *
     * @param center The center position of the circle.
     * @param args   Additional arguments (radius) needed to create the circle.
     * @param label  The label of the circle.
     * @return The created circle.
     */
    public Shape createCircle(Position center, double[] args, String label) {
        return new Circle(center, args[2], label);
    }

    /**
     * Creates a rectangle with the provided center, width, height, and label.
     *
     * @param center The center position of the rectangle.
     * @param args   Additional arguments (width and height) needed to create the rectangle.
     * @param label  The label of the rectangle.
     * @return The created rectangle.
     */
    public Shape createRectangle(Position center, double[] args, String label) {
        return new Rectangle(center, args[2], args[3], label);
    }
}

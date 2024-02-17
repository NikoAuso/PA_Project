package it.unicam.cs.pa.environment.shapes;

/**
 * An interface representing a factory for creating instances of various shapes.
 */
public interface ShapeFactory {
    /**
     * Creates a shape based on the provided parameters.
     *
     * @param label     The label for the shape.
     * @param shapeType The type of shape to create (e.g., "CIRCLE", "RECTANGLE").
     * @param args      The arguments needed to create the shape.
     * @return A new instance of the specified shape.
     */
    Shape createShape(String label, ShapeType shapeType, double[] args);
}

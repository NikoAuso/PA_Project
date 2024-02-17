package it.unicam.cs.pa.environment.shapes;

import java.util.stream.Stream;

/**
 * Enum representing types of shapes.
 * This enum provides a set of shape types that can be used in this application.
 */
public enum ShapeType {
    /**
     * A circular shape
     */
    CIRCLE("CIRCLE"),
    /**
     * A rectangular shape
     */
    RECTANGLE("RECTANGLE");

    /**
     * Represent the shape type as string
     */
    private final String type;

    /**
     * Constructs a ShapeType with the specified type.
     *
     * @param type The string representation of the shape type.
     */
    ShapeType(String type) {
        this.type = type;
    }

    /**
     * Selects a ShapeType based on the provided string representation.
     *
     * @param type The string representation of the shape type.
     * @return The ShapeType corresponding to the provided string representation.
     * @throws ShapeException if the specified shape type is unavailable.
     */
    public static ShapeType select(String type) {
        return Stream.of(ShapeType.values())
                .filter(t -> t.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new ShapeException("Specified shape is unavailable."));
    }
}

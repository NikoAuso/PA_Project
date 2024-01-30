package it.unicam.cs.pa.environment.shapes;

import java.util.stream.Stream;

public enum ShapeType {
    CIRCLE("CIRCLE"),
    RECTANGLE("RECTANGLE");

    private final String type;

    ShapeType(String type) {
        this.type = type;
    }

    public static ShapeType select(String type) {
        return Stream.of(ShapeType.values())
                .filter(t -> t.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new ShapeException("Specified shape is unavailable."));
    }
}

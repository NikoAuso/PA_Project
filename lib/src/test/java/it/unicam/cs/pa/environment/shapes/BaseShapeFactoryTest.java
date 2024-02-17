package it.unicam.cs.pa.environment.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseShapeFactoryTest {
    private BaseShapeFactory shapeFactory;
    private final String label = "Test_label";

    @BeforeEach
    void setUp() {
        shapeFactory = new BaseShapeFactory();
    }

    @Test
    void testCreateCircle() {
        double[] args = {1.0, 2.0, 3.0}; //x, y, radius

        Shape shape = shapeFactory.createShape(label, ShapeType.CIRCLE, args);

        assertInstanceOf(Circle.class, shape);
        Circle circle = (Circle) shape;

        assertAll(
                () -> assertEquals(label, circle.label()),
                () -> assertEquals(1.0, circle.center().x()),
                () -> assertEquals(2.0, circle.center().y()),
                () -> assertEquals(3.0, circle.radius())
        );
    }

    @Test
    void testCreateRectangle() {
        double[] args = {1.0, 2.0, 3.0, 4.0};

        Shape shape = shapeFactory.createShape(label, ShapeType.RECTANGLE, args);

        assertInstanceOf(Rectangle.class, shape);
        Rectangle rectangle = (Rectangle) shape;

        assertAll(
                () -> assertEquals(label, rectangle.label()),
                () -> assertEquals(1.0, rectangle.center().x()),
                () -> assertEquals(2.0, rectangle.center().y()),
                () -> assertEquals(3.0, rectangle.width()),
                () -> assertEquals(4.0, rectangle.height())
        );
    }
}

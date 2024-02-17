package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    private Circle circle;
    private final String label = "Test_label";
    private Position center;
    private double radius;

    @BeforeEach
    void setUp() {
        try {
            center = new Position(1.0, 2.0);
            radius = 3.0;

            circle = new Circle(center, radius, label);
        } catch (ShapeException e) {
            fail("Setup failed with exception: " + e.getMessage());
        }
    }

    @Test
    void testCircleConstructor() {
        assertDoesNotThrow(() -> {
            assertTrue(center.equals(circle.center()));
            assertEquals(radius, circle.radius());
            assertEquals(label, circle.label());
        });
        assertThrows(ShapeException.class, () -> new Circle(center, -2.0, label));
    }

    @Test
    void testGetType() {
        assertEquals(ShapeType.CIRCLE, circle.getType());
    }

    @Test
    void testGetLabel() {
        assertEquals(label, circle.getLabel());
    }

    @Test
    void testContainsCircle() {
        assertAll(
                () -> assertTrue(circle.contains(new Position(2.0, 3.0))),
                () -> assertFalse(circle.contains(new Position(5.0, 6.0)))
        );
    }
}

package it.unicam.cs.pa.shapes;

import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.environment.shapes.Circle;
import it.unicam.cs.pa.environment.shapes.ShapeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    private Circle circle;
    private Circle sameCircle;
    private Circle differentCircle;

    private final String label = "TestLabel";
    private Position center;
    private double radius;

    @BeforeEach
    void setUp() {
        try {
            center = new Position(1.0, 2.0);
            radius = 3.0;

            circle = new Circle(center, radius, label);
            sameCircle = new Circle(center, radius, "label 2");
            differentCircle = new Circle(new Position(4.0, 5.0), 3.0, "label 3");
        } catch (ShapeException e) {
            fail("Setup failed with exception: " + e.getMessage());
        }
    }

    @Test
    void testCircleConstruction() {
        double incorrectRadius = -1.0;

        assertDoesNotThrow(() -> {
            Circle circle = new Circle(center, radius, label);
            assertNotNull(circle);
            assertTrue(center.equals(circle.center()));
            assertEquals(radius, circle.radius());
        });
        assertThrows(ShapeException.class, () -> new Circle(center, incorrectRadius, label));
    }

    @Test
    void testContainsCircle() {
        assertAll(
                () -> assertTrue(circle.contains(new Position(2.0, 3.0))),
                () -> assertFalse(circle.contains(new Position(5.0, 6.0)))
        );
    }

    @Test
    void testEqualsCircle() {
        assertAll(
                () -> assertTrue(circle.equals(sameCircle)),
                () -> assertTrue(sameCircle.equals(circle)),

                () -> assertFalse(circle.equals(differentCircle)),
                () -> assertFalse(differentCircle.equals(circle))
        );
    }

    @Test
    void testToStringCircle() {
        String expected = "Shape: Circle->[X="+center.x()+", Y="+center.y()+", R="+radius+", Label="+label+"]";
        assertEquals(circle.toString(), expected);
    }
}

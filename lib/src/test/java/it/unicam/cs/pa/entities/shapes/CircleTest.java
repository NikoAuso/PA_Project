package it.unicam.cs.pa.entities.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import it.unicam.cs.pa.entities.Position;

import java.util.List;

class CircleTest {
    private Circle circle;
    private Circle sameCircle;
    private Circle differentCircle;

    @BeforeEach
    void setUp() {
        Position center = new Position(1.0, 2.0);
        double radius = 3.0;

        circle = new Circle(center, radius);
        sameCircle = new Circle(center, radius);

        differentCircle = new Circle(new Position(4.0, 5.0), 3.0);
    }

    @Test
    void testCircleConstruction() {
        Position position = new Position(1.0, 2.0);
        double radius = 5.0;

        assertDoesNotThrow(() -> {
            Circle circle = new Circle(position, radius);
            assertNotNull(circle);
            assertEquals(position, circle.center());
            assertEquals(radius, circle.radius());
        });
        assertThrows(ShapeException.class, () -> new Circle(position, -1.0));
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
        assertTrue(() -> circle.equals(sameCircle));
        assertTrue(() -> sameCircle.equals(circle));

        assertFalse(() -> circle.equals(differentCircle));
        assertFalse(() -> differentCircle.equals(circle));
    }

    @Test
    void testHashCodeCircle() {
        assertEquals(circle.hashCode(), sameCircle.hashCode());
        assertNotEquals(circle.hashCode(), differentCircle.hashCode());
    }
}

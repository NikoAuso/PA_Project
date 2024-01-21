package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    private Circle circle;
    private Circle sameCircle;
    private Circle differentCircle;

    @BeforeAll
    static void testCircleConstruction() {
        Position position = new Position(1.0, 2.0);
        double radius = 5.0;

        assertDoesNotThrow(() -> {
            Circle circle = new Circle(position, radius, "label 4");
            assertNotNull(circle);
            assertEquals(position, circle.center());
            assertEquals(radius, circle.radius());
        });
        assertThrows(ShapeException.class, () -> new Circle(new Position(1.0, 2.0), -1.0, "label 5"));
    }

    @BeforeEach
    void setUp() {
        Position center = new Position(1.0, 2.0);
        double radius = 3.0;

        circle = new Circle(center, radius, "label 1");
        sameCircle = new Circle(center, radius, "label 2");
        differentCircle = new Circle(new Position(4.0, 5.0), 3.0, "label 3");
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
        assertTrue(circle.equals(sameCircle));
        assertTrue(sameCircle.equals(circle));

        assertFalse(circle.equals(differentCircle));
        assertFalse(differentCircle.equals(circle));
    }

    @Test
    void testToStringCircle() {
        assertEquals(circle.toString(), "Shape: Circle->[X=1.0, Y=2.0, R=3.0, Label=label 1]");
    }
}

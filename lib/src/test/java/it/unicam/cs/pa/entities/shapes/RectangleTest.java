package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    private Rectangle rectangle;
    private Rectangle sameRectangle;
    private Rectangle differentRectangle;

    @BeforeAll
    static void testRectangleConstruction() {
        Position position = new Position(1.0, 2.0);
        double width = 4.0;
        double height = 2.0;

        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Rectangle(position, height, width, "label 4");
            assertNotNull(rectangle);
            assertEquals(position, rectangle.center());
            assertEquals(height, rectangle.height());
            assertEquals(width, rectangle.width());
        });
        assertThrows(ShapeException.class, () -> new Rectangle(new Position(1.0, 2.0), -1.0, 4.0, "label 5"));
        assertThrows(ShapeException.class, () -> new Rectangle(new Position(1.0, 2.0), 3.0, -4.0, "label 6"));
    }

    @BeforeEach
    void setUp() {
        Position center = new Position(1.0, 2.0);
        double width = 3.0;
        double height = 4.0;

        rectangle = new Rectangle(center, height, width, "label 1");
        sameRectangle = new Rectangle(center, height, width, "label 2");
        differentRectangle = new Rectangle(new Position(4.0, 5.0), 3.0, 3.0, "label 3");
    }

    @Test
    void testContainsRectangle() {
        assertAll(
                () -> assertTrue(rectangle.contains(new Position(2.0, 3.0))),
                () -> assertFalse(rectangle.contains(new Position(5.0, 6.0)))
        );
    }

    @Test
    void testEqualsRectangle() {
        assertTrue(() -> rectangle.equals(sameRectangle));
        assertTrue(() -> sameRectangle.equals(rectangle));

        assertFalse(() -> rectangle.equals(differentRectangle));
        assertFalse(() -> differentRectangle.equals(rectangle));
    }

    @Test
    void testToStringCircle() {
        assertEquals(rectangle.toString(), "Shape: Rectangle->[X=1.0, Y=2.0, W=3.0, H=4.0, Label=label 1]");
    }
}

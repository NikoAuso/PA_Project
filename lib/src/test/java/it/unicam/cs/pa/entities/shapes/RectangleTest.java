package it.unicam.cs.pa.entities.shapes;

import it.unicam.cs.pa.entities.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    private Rectangle rectangle;
    private Rectangle sameRectangle;
    private Rectangle differentRectangle;

    @BeforeEach
    void setUp() {
        Position center = new Position(1.0, 2.0);
        double width = 3.0;
        double height = 4.0;

        rectangle = new Rectangle(center, height, width);
        sameRectangle = new Rectangle(center, height, width);

        differentRectangle = new Rectangle(new Position(4.0, 5.0), 3.0, 3.0);
    }

    @Test
    void testRectangleConstruction() {
        Position position = new Position(1.0, 2.0);
        double width = 4.0;
        double height = 2.0;

        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Rectangle(position, height, width);
            assertNotNull(rectangle);
            assertEquals(position, rectangle.center());
            assertEquals(height, rectangle.height());
            assertEquals(width, rectangle.width());
        });
        assertThrows(ShapeException.class, () -> new Rectangle(new Position(1.0, 2.0), -1.0, 4.0));
        assertThrows(ShapeException.class, () -> new Rectangle(new Position(1.0, 2.0), 3.0, -4.0));
    }

    @Test
    void testContainsRectangle() {
        Position insidePosition = new Position(2.0, 3.0);
        Position outsidePosition = new Position(5.0, 6.0);

        assertTrue(rectangle.contains(insidePosition));
        assertFalse(rectangle.contains(outsidePosition));
    }

    @Test
    void testEqualsRectangle() {
        assertTrue(() -> rectangle.equals(sameRectangle));
        assertTrue(() -> sameRectangle.equals(rectangle));

        assertFalse(() -> rectangle.equals(differentRectangle));
        assertFalse(() -> differentRectangle.equals(rectangle));
    }

    @Test
    void testHashCodeRectangle() {
        assertEquals(rectangle.hashCode(), sameRectangle.hashCode());
        assertNotEquals(rectangle.hashCode(), differentRectangle.hashCode());
    }
}

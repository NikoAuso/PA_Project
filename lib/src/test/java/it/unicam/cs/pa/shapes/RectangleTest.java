package it.unicam.cs.pa.shapes;

import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.ShapeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    private Rectangle rectangle;
    private Rectangle sameRectangle;
    private Rectangle differentRectangle;

    private final String label = "TestLabel";
    private Position center;
    private double height;
    private double width;

    @BeforeEach
    void setUp() {
        try {
            center = new Position(1.0, 2.0);
            width = 3.0;
            height = 4.0;

            rectangle = new Rectangle(center, width, height, label);
            sameRectangle = new Rectangle(center, width, height, "label 2");
            differentRectangle = new Rectangle(new Position(4.0, 5.0), 3.0, 3.0, "label 3");
        } catch (ShapeException e) {
            fail("Setup failed with exception: " + e.getMessage());
        }
    }

    @Test
    void testRectangleConstruction() {
        double incorrectWidth = -4.0;
        double incorrectHeight = -2.0;

        assertDoesNotThrow(() -> {
            Rectangle rectangle = new Rectangle(center, width, height, label);
            assertNotNull(rectangle);
            assertEquals(center, rectangle.center());
            assertEquals(height, rectangle.height());
            assertEquals(width, rectangle.width());
        });
        assertThrows(ShapeException.class, () -> new Rectangle(center, width, incorrectHeight, label));
        assertThrows(ShapeException.class, () -> new Rectangle(center, incorrectWidth, height, label));
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
        assertAll(
                () -> assertTrue(() -> rectangle.equals(sameRectangle)),
                () -> assertTrue(() -> sameRectangle.equals(rectangle)),

                () -> assertFalse(() -> rectangle.equals(differentRectangle)),
                () -> assertFalse(() -> differentRectangle.equals(rectangle))
        );
    }

    @Test
    void testToStringCircle() {
        String expected = "Shape: Rectangle->[X=" + center.x() + ", Y=" + center.y() + ", W=" + width + ", H=" + height + ", Label=" + label + "]";
        assertEquals(rectangle.toString(), expected);
    }
}

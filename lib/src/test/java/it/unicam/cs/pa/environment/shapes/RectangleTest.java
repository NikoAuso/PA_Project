package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    private Rectangle rectangle;
    private final String label = "Test_label";
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
        } catch (ShapeException e) {
            fail("Setup failed with exception: " + e.getMessage());
        }
    }

    @Test
    void testRectangleConstruction() {
        assertDoesNotThrow(() -> {
            assertEquals(center, rectangle.center());
            assertEquals(height, rectangle.height());
            assertEquals(width, rectangle.width());
        });
        assertThrows(ShapeException.class, () -> new Rectangle(center, width, -5.0, label));
        assertThrows(ShapeException.class, () -> new Rectangle(center, -2.0, height, label));
    }

    @Test
    void testGetType() {
        assertEquals(ShapeType.RECTANGLE, rectangle.getType());
    }

    @Test
    void testGetLabel() {
        assertEquals(label, rectangle.getLabel());
    }

    @Test
    void testContainsRectangle() {
        assertAll(
                () -> assertTrue(rectangle.contains(new Position(2.0, 3.0))),
                () -> assertFalse(rectangle.contains(new Position(5.0, 6.0)))
        );
    }
}

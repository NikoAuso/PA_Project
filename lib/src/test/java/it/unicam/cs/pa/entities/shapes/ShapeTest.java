package it.unicam.cs.pa.entities.shapes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {
    @Test
    void testGenerateRandomShape() {
        Shape randomShape = Shape.generateRandomShape();
        assertNotNull(randomShape);
        assertTrue(randomShape instanceof Circle || randomShape instanceof Rectangle);
    }

    @Test
    void testGenerateRandomShapes() {
        int total = 5;
        List<Shape> randomShapes = Shape.generateRandomShape(total);
        assertNotNull(randomShapes);
        assertEquals(total, randomShapes.size());
        for (Shape shape : randomShapes) {
            assertNotNull(shape);
            assertTrue(shape instanceof Circle || shape instanceof Rectangle);
        }
    }
}

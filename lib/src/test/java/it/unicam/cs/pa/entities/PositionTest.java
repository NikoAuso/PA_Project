package it.unicam.cs.pa.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private Position position;

    @BeforeAll
    static void testPositionConstruction() {
        double x = Math.random() * Double.MAX_VALUE;
        double y = Math.random() * Double.MAX_VALUE;

        assertDoesNotThrow(() -> {
            Position position = new Position(x, y);
            assertNotNull(position);
            assertEquals(x, position.x());
            assertEquals(y, position.y());
        });
        assertThrows(PositionException.class, () -> new Position(-1.0, 2.0));
    }

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0);
    }

    @Test
    void testRandomPositionMethod(){
        assertDoesNotThrow(() -> {
            Position position = Position.random();
            assertNotNull(position);
            assertTrue(position.x() >= 0 && position.y() >= 0);
        });
    }

    @Test
    void testEqualsPositionMethod() {
        Position equals_position = new Position(1.0, 2.0);
        Position wrong_position = new Position(11, 12);

        assertDoesNotThrow(() -> {
            assertTrue(position.equals(equals_position));
            assertTrue(equals_position.equals(position));

            assertFalse(position.equals(wrong_position));
            assertFalse(wrong_position.equals(position));

            assertFalse(wrong_position.equals(equals_position));
            assertFalse(equals_position.equals(wrong_position));
        });
    }

    @Test
    void testDistanceToPositionMethod() {
        Position position1 = new Position(4.0, 6.0);

        assertAll(() -> {
            assertEquals(0.0, position.distanceTo(position));
            assertEquals(5.0, position1.distanceTo(position));
        });
    }
}

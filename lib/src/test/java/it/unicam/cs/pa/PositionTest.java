package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.environment.PositionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private Position position;
    Position equals_position;
    Position wrong_position;

    @BeforeAll
    static void testPositionConstruction() {
        double x = 1.0;
        double y = 2.0;
        Position position = new Position(x, y);

        assertDoesNotThrow(() -> {
            assertNotNull(position);
            assertEquals(x, position.x());
            assertEquals(y, position.y());
        });

        assertAll(() -> {
            assertThrows(PositionException.class, () -> new Position(Double.NaN, 2.0));
            assertThrows(PositionException.class, () -> new Position(1.0, Double.NaN));
            assertThrows(PositionException.class, () -> new Position(Double.POSITIVE_INFINITY, 2.0));
            assertThrows(PositionException.class, () -> new Position(1.0, Double.NEGATIVE_INFINITY));
            assertThrows(PositionException.class, () -> new Position(1.0, 1e16 + 1));
            assertThrows(PositionException.class, () -> new Position(-1e16 - 1, 2.0));
        });
    }

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0);
        equals_position = new Position(1.0, 2.0);
        wrong_position = new Position(11, 12);
    }

    @Test
    void testRandomPositionMethod() {
        Position randomPosition = Position.random(10);
        assertDoesNotThrow(() -> {
            assertNotNull(randomPosition);
            assertTrue(randomPosition.x() >= -1e15 && randomPosition.x() <= 1e15);
            assertTrue(randomPosition.y() >= -1e15 && randomPosition.y() <= 1e15);
        });
    }

    @Test
    void testEqualsPositionMethod() {
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

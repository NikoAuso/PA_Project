package it.unicam.cs.pa.environment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private Position position;
    private Position equals_position;
    private Position wrong_position;

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0);
        equals_position = new Position(1.0, 2.0);
        wrong_position = new Position(11, 12);
    }

    @Test
    void testPositionConstructor() {
        assertAll(() -> {
            assertNotNull(position);
            assertEquals(1.0, position.x());
            assertEquals(2.0, position.y());

            assertThrows(PositionException.class, () -> new Position(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
            assertThrows(PositionException.class, () -> new Position(Double.NaN, Double.NaN));
            assertThrows(PositionException.class, () -> new Position(Double.MAX_VALUE, Double.MAX_VALUE));
        });
    }

    @Test
    void testPositionConstructor_InvalidInputValue() {
        assertAll(() -> {
            assertThrows(PositionException.class, () -> new Position(Double.NaN, 2.0));
            assertThrows(PositionException.class, () -> new Position(1.0, Double.NaN));
            assertThrows(PositionException.class, () -> new Position(Double.POSITIVE_INFINITY, 2.0));
            assertThrows(PositionException.class, () -> new Position(1.0, Double.NEGATIVE_INFINITY));
            assertThrows(PositionException.class, () -> new Position(1.0, 1e16 + 1));
            assertThrows(PositionException.class, () -> new Position(-1e16 - 1, 2.0));
        });
    }

    @Test
    void testRandomPositionWithBoundMethod() {
        Position randomPosition = Position.random(10);
        assertDoesNotThrow(() -> {
            assertNotNull(randomPosition);
            assertTrue(randomPosition.x() >= -10 && randomPosition.x() <= 10);
            assertTrue(randomPosition.y() >= -10 && randomPosition.y() <= 10);
        });
    }

    @Test
    void testRandomPositionWithMinMaxMethod() {
        Position min = new Position(5, 5);
        Position max = new Position(10, 10);
        Position position = Position.random(min, max);
        assertDoesNotThrow(() -> {
            assertNotNull(position);
            assertTrue(position.x() >= 5 && position.x() <= 10);
            assertTrue(position.y() >= 5 && position.y() <= 10);
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

    @Test
    void testAveragePositionsMethod() {
        List<Position> positions = new ArrayList<>();

        Optional<Position> average_empty = Position.average(positions);
        assertFalse(average_empty.isPresent());

        positions.add(new Position(1, 1));
        positions.add(new Position(3, 3));
        positions.add(new Position(5, 5));

        Optional<Position> average_populated = Position.average(positions);

        assertAll(() -> {
            assertTrue(average_populated.isPresent());
            assertEquals(3.0, average_populated.get().x());
            assertEquals(3.0, average_populated.get().y());
        });
    }
}

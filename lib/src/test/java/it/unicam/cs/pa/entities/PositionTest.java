package it.unicam.cs.pa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.random.RandomGenerator;

class PositionTest {
    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0);
    }

    @Test
    void testPositionConstruction() {
        double x = RandomGenerator.getDefault().nextDouble();
        double y = RandomGenerator.getDefault().nextDouble();

        assertDoesNotThrow(() -> {
            Position position = new Position(x, y);
            assertNotNull(position);
            assertEquals(x, position.x());
            assertEquals(y, position.y());
        });
        assertThrows(PositionException.class, () -> new Position(-1.0, 2.0));
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
    void testGenerateRandomPositionsMethod() {
        int numberOfPositions = 10;

        assertDoesNotThrow(() -> {
            List<Position> randomPositions = Position.generateRandomPositions(numberOfPositions);
            assertNotNull(randomPositions);
            assertEquals(numberOfPositions, randomPositions.size());
            for (Position position : randomPositions) {
                assertNotNull(position);
            }
        });
        assertThrows(PositionException.class, () -> Position.generateRandomPositions(-1));
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

package it.unicam.cs.pa.robot;

import it.unicam.cs.pa.environment.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {
    private BasicRobot robot;

    @BeforeEach
    void setUp() {
        Position initialPosition = new Position(0, 0);
        robot = new BasicRobot(initialPosition);
    }

    @Test
    void testRobotConstructor() {
        Position position = robot.position();
        assertEquals(0, position.x());
        assertEquals(0, position.y());

        Position direction = robot.direction();
        assertEquals(0, direction.x());
        assertEquals(0, direction.y());

        assertEquals("", robot.currentLabel());

        assertTrue(robot.canMove());
    }

    @Test
    void testSetDirection() {
        robot.setDirection(new Position(1, 1));
        Position direction = robot.direction();

        assertEquals(1 / Math.sqrt(2), direction.x());
        assertEquals(1 / Math.sqrt(2), direction.y());
    }

    @Test
    void testSetSpeed() {
        robot.setSpeed(2.5);
        assertEquals(2.5, robot.speed());
    }

    @Test
    void testMove() {
        robot.setDirection(new Position(1, 1));
        robot.setSpeed(2);
        robot.move();
        Position newPosition = robot.position();

        assertEquals(2 / Math.sqrt(2), newPosition.x());
        assertEquals(2 / Math.sqrt(2), newPosition.y());
    }

    @Test
    void testSignal() {
        robot.signal("test");
        assertTrue(robot.isSignaling("test"));
    }

    @Test
    void testUnsignal() {
        robot.signal("test");
        robot.unsignal("test");
        assertFalse(robot.isSignaling("test"));
    }

    @Test
    void testIsSignaling() {
        robot.signal("test");
        assertTrue(robot.isSignaling("test"));
    }

    @Test
    void testStop() {
        robot.stop();
        assertFalse(robot.canMove());
    }
}

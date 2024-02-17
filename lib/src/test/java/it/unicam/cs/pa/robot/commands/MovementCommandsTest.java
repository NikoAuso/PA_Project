package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.BasicRobot;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.movement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovementCommandsTest {
    private CommandParser parser;
    private Environment environment;
    private final String label = "TEST_LABEL";

    @BeforeEach
    void setUp() {
        parser = new CommandParser();
        Robot robot = new BasicRobot(new Position(0, 0));
        Shape shape = new Rectangle(new Position(0.0, 0.0), 3.0, 1.0, label);
        environment = new Environment();
        environment.getShapes().add(shape);
        environment.getRobots().add(robot);
    }

    @Test
    void testMoveCommand() {
        parser.parseRobotProgram("MOVE 1.0 1.0 1.0");
        List<Command> commands = parser.getCommands();
        Position newPosition = calculateDirection(new Position(1.0, 1.0));
        assertDoesNotThrow(() -> {
            assertInstanceOf(Move.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertEquals(newPosition.x(), environment.getRobots().get(0).direction().x());
            assertEquals(newPosition.y(), environment.getRobots().get(0).direction().y());
            assertEquals(1, environment.getRobots().get(0).speed());
        });
    }

    @Test
    void testMoveRandomCommand() throws CommandException {
        parser.parseRobotProgram("MOVE RANDOM 1 2 3 4 5");
        List<Command> commands = parser.getCommands();

        Position position1 = new Position(1, 2);
        Position position2 = new Position(3, 4);

        assertDoesNotThrow(() -> {
            assertInstanceOf(MoveRandom.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertTrue(environment.getRobots().get(0).position().x() + position1.x() >= position1.x() && environment.getRobots().get(0).position().x() + position2.x() <= position2.x());
            assertTrue(environment.getRobots().get(0).position().y() + position1.y() >= position1.y() && environment.getRobots().get(0).position().y() + position2.y() <= position2.y());
            assertEquals(5, environment.getRobots().get(0).speed());
        });
    }

    @Test
    void testStopCommand() throws CommandException {
        parser.parseRobotProgram("STOP");
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Stop.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertFalse(environment.getRobots().get(0).canMove());
        });
    }

    @Test
    void testFollowCommand() throws CommandException {
        environment.getRobots().add(new BasicRobot(new Position(2.0, 5.0)));
        environment.getRobots().add(new BasicRobot(new Position(-1.0, 3.0)));
        parser.parseRobotProgram(String.format("FOLLOW %s 10.0 2.0", label));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Follow.class, commands.get(0));

            environment.getRobots().get(1).signal(label);

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertEquals(label, environment.getRobots().get(1).currentLabel());
            assertEquals(calculateDirection(new Position(2.0, 5.0)).x(), environment.getRobots().get(0).direction().x());
            assertEquals(calculateDirection(new Position(2.0, 5.0)).y(), environment.getRobots().get(0).direction().y());
        });
    }

    @Test
    void testSignalCommand() throws CommandException {
        parser.parseRobotProgram(String.format("SIGNAL %s", label));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Signal.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertEquals(label, environment.getRobots().get(0).currentLabel());
            assertTrue(environment.getRobots().get(0).isSignaling(label));
        });
    }

    @Test
    void testUnsignalCommand() throws CommandException {
        environment.getRobots().get(0).signal(label);
        parser.parseRobotProgram(String.format("UNSIGNAL %s", label));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Unsignal.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));

            assertFalse(environment.getRobots().get(0).isSignaling(label));
        });
    }

    private Position calculateDirection(Position direction) {
        double deltaX = direction.x() - this.environment.getRobots().get(0).position().x();
        double deltaY = direction.y() - this.environment.getRobots().get(0).position().y();

        double module = Math.hypot(deltaX, deltaY);
        return new Position(deltaX / module, deltaY / module);
    }
}

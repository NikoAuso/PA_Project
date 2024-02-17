package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.BasicRobot;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.loop.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class LoopCommandsTest {
    private CommandParser parser;
    private Environment environment;

    @BeforeEach
    void setUp() {
        parser = new CommandParser();
        Robot robot = new BasicRobot(new Position(0, 0));
        Shape shape = new Rectangle(new Position(0.0, 0.0), 3.0, 1.0, "TEST_LABEL");
        environment = new Environment();
        environment.getShapes().add(shape);
        environment.getRobots().add(robot);
    }

    @Test
    void testDoForeverCommand() throws CommandException, IOException {
        parser.parseRobotProgram(new File((Objects.requireNonNull(getClass().getResource("/program/test_doforever_program.txt"))).getFile()));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(DoForever.class, commands.get(0));

            for (int i = 0; i < 3; i++) {
                commands.get(0).execute(environment, environment.getRobots().get(0));
                assertEquals(i + 1, environment.getRobots().get(0).currentCommandIndex());
            }
        });
    }

    @Test
    void testRepeatCommand() throws CommandException, IOException {
        parser.parseRobotProgram(new File((Objects.requireNonNull(getClass().getResource("/program/test_repeat_program.txt"))).getFile()));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Repeat.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));
            assertEquals(1, environment.getRobots().get(0).currentCommandIndex());
            commands.get(0).execute(environment, environment.getRobots().get(0));
            assertEquals(2, environment.getRobots().get(0).currentCommandIndex());
            commands.get(0).execute(environment, environment.getRobots().get(0));
            assertEquals(3, environment.getRobots().get(0).currentCommandIndex());
        });
    }

    @Test
    void testUntilCommand() throws CommandException, IOException {
        parser.parseRobotProgram(new File((Objects.requireNonNull(getClass().getResource("/program/test_until_program.txt"))).getFile()));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Until.class, commands.get(0));

            commands.get(0).execute(environment, environment.getRobots().get(0));
            assertEquals(1, environment.getRobots().get(0).currentCommandIndex());
        });
    }

    @Test
    void testContinueCommand() throws CommandException, IOException {
        parser.parseRobotProgram(new File((Objects.requireNonNull(getClass().getResource("/program/test_continue_program.txt"))).getFile()));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Continue.class, commands.get(1));

            commands.get(0).execute(environment, environment.getRobots().get(0));
            assertEquals(0, environment.getRobots().get(0).currentCommandIndex());

            environment.getRobots().get(0).increaseCurrentCommandIndex();

            commands.get(1).execute(environment, environment.getRobots().get(0));
            assertEquals(2, environment.getRobots().get(0).currentCommandIndex());
        });
    }

    @Test
    void testDoneCommand() throws CommandException, IOException {
        parser.parseRobotProgram(new File((Objects.requireNonNull(getClass().getResource("/program/test_doforever_program.txt"))).getFile()));
        List<Command> commands = parser.getCommands();
        assertDoesNotThrow(() -> {
            assertInstanceOf(Done.class, commands.get(2));

            commands.get(0).execute(environment, environment.getRobots().get(0));
            commands.get(1).execute(environment, environment.getRobots().get(0));
            commands.get(2).execute(environment, environment.getRobots().get(0));
            assertEquals(0, environment.getRobots().get(0).currentCommandIndex());
        });
    }
}

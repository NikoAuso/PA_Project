package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.robot.commands.loop.Repeat;
import it.unicam.cs.pa.robot.commands.movement.Move;
import it.unicam.cs.pa.robot.commands.movement.Signal;
import it.unicam.cs.pa.robot.commands.movement.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {
    private CommandParser parser;
    private File testFile;

    @BeforeEach
    void setUp() {
        parser = new CommandParser();
        testFile = new File((Objects.requireNonNull(getClass().getResource("/program/test_program.txt"))).getFile());
    }

    @Test
    void testParseRobotProgramFromFile() throws IOException, CommandException {
        parser.parseRobotProgram(testFile);
        List<Command> commands = parser.getCommands();
        assertEquals(4, commands.size());
        assertInstanceOf(Move.class, commands.get(0));
        assertInstanceOf(Signal.class, commands.get(1));
        assertInstanceOf(Stop.class, commands.get(2));
        assertInstanceOf(Repeat.class, commands.get(3));
    }

    @Test
    void testParseRobotProgramFromPath() throws IOException, CommandException {
        Path path = Path.of(testFile.toURI());
        parser.parseRobotProgram(path);
        List<Command> commands = parser.getCommands();
        assertEquals(4, commands.size());
        assertInstanceOf(Move.class, commands.get(0));
        assertInstanceOf(Signal.class, commands.get(1));
        assertInstanceOf(Stop.class, commands.get(2));
        assertInstanceOf(Repeat.class, commands.get(3));
    }

    @Test
    void testParseRobotProgramFromString() throws CommandException {
        String code = """
                MOVE 1 0.5 0.3
                SIGNAL test
                STOP
                REPEAT 3
                """;
        parser.parseRobotProgram(code);
        List<Command> commands = parser.getCommands();
        assertEquals(4, commands.size());
        assertInstanceOf(Move.class, commands.get(0));
        assertInstanceOf(Signal.class, commands.get(1));
        assertInstanceOf(Stop.class, commands.get(2));
        assertInstanceOf(Repeat.class, commands.get(3));
    }

    @Test
    void testParseRobotProgram_InvalidInputData() {
        String invalidProgram = """
                MOVE 1 -1.0
                INVALID_COMMAND
                STOP
                REPEAT 3
                """;
        assertThrows(CommandException.class, () -> parser.parseRobotProgram(invalidProgram));
    }
}

package it.unicam.cs.pa.environment;

import it.unicam.cs.pa.environment.shapes.ShapeChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvironmentParserTest {
    private EnvironmentParser parser;

    private File rightEnvFile;
    private File wrongEnvFile;

    @BeforeEach
    void setUp() {
        parser = new EnvironmentParser(ShapeChecker.DEFAULT_CHECKER);
        rightEnvFile = new File((Objects.requireNonNull(getClass().getResource("/environment/right_environment.txt"))).getFile());
        wrongEnvFile = new File((Objects.requireNonNull(getClass().getResource("/environment/wrong_environment.txt"))).getFile());
    }

    @Test
    void testParseEnvironmentFromFile() throws IOException, EnvironmentException {
        parser.parseEnvironment(rightEnvFile);
        assertEquals(3, parser.getShapes().size());
    }

    @Test
    void testParseEnvironmentFromPath() throws EnvironmentException, IOException {
        Path path = Path.of(rightEnvFile.toURI());
        parser.parseEnvironment(path);
        assertEquals(3, parser.getShapes().size());
    }

    @Test
    void testParseEnvironmentFromString() throws EnvironmentException {
        String data = """
                shape_a CIRCLE 1.0 1.2 5.0
                shape_b CIRCLE 10.4 10.6 5
                shape_c RECTANGLE 4.4 -5 4 4
                """;
        parser.parseEnvironment(data);
        assertEquals(3, parser.getShapes().size());
    }

    @Test
    void testParseEnvironment_InvalidInputData() {
        EnvironmentException exception = assertThrows(EnvironmentException.class, () -> parser.parseEnvironment(wrongEnvFile));
        assertEquals("Syntax error at line 1", exception.getMessage());
    }
}

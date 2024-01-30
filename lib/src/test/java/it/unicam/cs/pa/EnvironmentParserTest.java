package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.EnvironmentParser;
import it.unicam.cs.pa.environment.shapes.ShapeChecker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class EnvironmentParserTest {
    @Test
    void testParseEnvironment() throws IOException {
        EnvironmentParser shapeParser = new EnvironmentParser(ShapeChecker.DEFAULT_CHECKER);
        shapeParser.parseEnvironment(Path.of("/home/nikoauso/Scrivania/PA_PROJECT/app/src/main/resources/it/unicam/cs/pa/app/files/environment.txt"));
        System.out.println(shapeParser);
    }
}

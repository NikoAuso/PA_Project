package it.unicam.cs.pa.environment;

import it.unicam.cs.pa.environment.shapes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Parses environment data and creates shapes based on the provided information, such as file or strings.
 */
public class EnvironmentParser {
    private int lineCounter = 0; //counter to keep track of the current line being processed.
    private final ShapeChecker checker; //used to validate shape parameters before creating shapes.
    private final ShapeFactory shapeFactory; //used to create instances of shapes based on the parsed data.
    private final List<Shape> shapes; //list of Shape objects parsed.

    /**
     * Constructs an EnvironmentParser with the specified shape checker.
     *
     * @param checker The shape checker used to validate shape parameters.
     */
    public EnvironmentParser(ShapeChecker checker) {
        this.checker = checker;
        this.shapeFactory = new BaseShapeFactory();
        this.shapes = new ArrayList<>();
    }

    /**
     * Parses the environment data from a file and adds the parsed shapes to the list of shapes.
     *
     * @param file The file containing the environment data.
     * @throws IOException          If an I/O error occurs while reading the file.
     * @throws EnvironmentException If there is an error parsing the environment data.
     */
    public synchronized void parseEnvironment(File file) throws IOException, EnvironmentException {
        parseEnvironment(file.toPath());
    }

    /**
     * Parses the environment data from a string and adds the parsed shapes to the list of shapes.
     *
     * @param data The string containing the environment data.
     * @throws EnvironmentException If there is an error parsing the environment data.
     */
    public synchronized void parseEnvironment(String data) throws EnvironmentException {
        parseEnvironment(List.of(data.split("\n")));
    }

    /**
     * Parses the environment data from a file path and adds the parsed shapes to the list of shapes.
     *
     * @param path The path to the file containing the environment data.
     * @throws IOException          If an I/O error occurs while reading the file.
     * @throws EnvironmentException If there is an error parsing the environment data.
     */
    public synchronized void parseEnvironment(Path path) throws IOException, EnvironmentException {
        parseEnvironment(Files.readAllLines(path));
    }

    /**
     * Parses the environment data from a list of strings and adds the parsed shapes to the list of shapes.
     *
     * @param lines The list of strings representing the environment data.
     * @throws EnvironmentException If there is an error parsing the environment data.
     */
    private void parseEnvironment(List<String> lines) throws EnvironmentException {
        for (String line : lines) {
            lineCounter++;
            String[] elements = line.trim().toUpperCase().split(" ");
            if (checker.checkParameters(elements)) {
                shapes.add(shapeFactory.createShape(
                        elements[0],
                        ShapeType.select(elements[1]),
                        IntStream.range(2, elements.length).mapToDouble(i -> Double.parseDouble(elements[i])).toArray())
                );
            } else {
                throw new EnvironmentException(String.format("Syntax error at line %d", lineCounter));
            }
        }
    }

    /**
     * Gets the list of shapes parsed from the environment data.
     *
     * @return The list of shapes.
     */
    public List<Shape> getShapes() {
        return this.shapes;
    }
}

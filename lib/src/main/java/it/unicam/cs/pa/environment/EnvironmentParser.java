package it.unicam.cs.pa.environment;

import it.unicam.cs.pa.environment.shapes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EnvironmentParser {
    private int lineCounter = 0;
    private final ShapeChecker checker;
    private ShapeFactory shapeFactory;
    private List<Shape> shapes;

    public EnvironmentParser(ShapeChecker checker) {
        this.checker = ShapeChecker.DEFAULT_CHECKER;
        this.shapeFactory = new BaseShapeFactory();
        this.shapes = new ArrayList<>();
    }

    public synchronized void parseEnvironment(File file) throws IOException, EnvironmentException {
        parseEnvironment(file.toPath());
    }

    public synchronized void parseEnvironment(String data) throws EnvironmentException {
        parseEnvironment(List.of(data.split("\n")));
    }

    public synchronized void parseEnvironment(Path path) throws IOException, EnvironmentException {
        parseEnvironment(Files.readAllLines(path));
    }

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

    public List<Shape> getShapes() {
        return this.shapes;
    }
}

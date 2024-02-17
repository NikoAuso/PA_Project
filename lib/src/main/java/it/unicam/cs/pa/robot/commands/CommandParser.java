package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.commands.loop.*;
import it.unicam.cs.pa.robot.commands.movement.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses program data and creates a list of commands based on the provided information, such as file or strings.
 */
public final class CommandParser {
    private int linecounter = 0; // counter to keep track of the current line being processed.
    private final List<Command> commands; // list of Command objects parsed.

    /**
     * Constructs a new CommandParser instance with an empty list of commands.
     */
    public CommandParser() {
        this.commands = new ArrayList<>();
    }

    /**
     * Parses a robot program from a file.
     *
     * @param sourceFile The file containing the robot program.
     * @throws IOException      If an I/O error occurs.
     * @throws CommandException If a syntax error or unknown command is encountered.
     */
    public synchronized void parseRobotProgram(File sourceFile) throws IOException, CommandException {
        parseRobotProgram(sourceFile.toPath());
    }

    /**
     * Parses a robot program from a file specified by its path.
     *
     * @param path The path to the file containing the robot program.
     * @throws IOException      If an I/O error occurs.
     * @throws CommandException If a syntax error or unknown command is encountered.
     */
    public synchronized void parseRobotProgram(Path path) throws IOException, CommandException {
        parseRobotProgram(Files.readAllLines(path));
    }

    /**
     * Parses a robot program from a string of code.
     *
     * @param code The string containing the robot program code.
     * @throws CommandException If a syntax error or unknown command is encountered.
     */
    public synchronized void parseRobotProgram(String code) throws CommandException {
        parseRobotProgram(List.of(code.split("\n")));
    }

    /**
     * Parses a list of strings representing robot program code.
     * Each line is parsed to identify the corresponding command, and the appropriate Command objects are added to the list of commands.
     * Throws a CommandException if an unknown command is encountered during parsing.
     *
     * @param lines The list of strings representing robot program code.
     * @throws CommandException if an unknown command is encountered during parsing.
     */
    private void parseRobotProgram(List<String> lines) throws CommandException {
        for (String line : lines) {
            linecounter++;
            Optional<RobotCommands> oCommand = RobotCommands.selectCommand(line);
            if (oCommand.isPresent()) {
                String[] elements = line.trim().toUpperCase().split(" ");
                switch (oCommand.get()) {
                    case MOVE -> addMoveMethods(elements);
                    case SIGNAL -> addSignalMethod(elements);
                    case UNSIGNAL -> addUnSignalMethod(elements);
                    case FOLLOW -> addFollowMethod(elements);
                    case STOP -> addStopMethod(elements);
                    case CONTINUE -> addContinueMethod(elements);
                    case REPEAT -> addRepeatMethod(elements);
                    case UNTIL -> addUntilMethod(elements);
                    case FOREVER -> addForeverMethod(elements);
                    case DONE -> addDoneMethod(elements);
                }
            } else
                throw new CommandException(String.format("Unknown command at line %d", this.linecounter));
        }
    }

    // Loop commands
    /*###################################################*/

    /**
     * Adds a 'DO FOREVER' loop command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addForeverMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new DoForever(linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds an 'UNTIL' loop command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addUntilMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Until(elements[1], linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'REPEAT' loop command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     * @throws CommandException If the command syntax is incorrect.
     */
    private void addRepeatMethod(String[] elements) {
        if (elements.length == 2) {
            try {
                commands.add(new Repeat(Integer.parseInt(elements[1]), linecounter - 1));
            } catch (NumberFormatException e) {
                throwSyntaxErrorException();
            }
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'CONTINUE' loop command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addContinueMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Continue(Integer.parseInt(elements[1]), linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'DONE' command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addDoneMethod(String[] elements) {
        if (elements.length == 1) {
            commands.add(new Done());
        } else {
            throwSyntaxErrorException();
        }
    }
    /*###################################################*/


    // Movement commands
    /*###################################################*/

    /**
     * Adds a 'STOP' movement command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addStopMethod(String[] elements) {
        if (elements.length == 1) {
            commands.add(new Stop());
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'FOLLOW' movement command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addFollowMethod(String[] elements) {
        if (elements.length == 4) {
            double[] args = toDoubleArray(2, elements);
            assert args != null;
            commands.add(new Follow(elements[1], args[0], args[1]));
        } else {
            throwSyntaxErrorException();
        }

    }

    /**
     * Adds an 'UNSIGNAL' command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addUnSignalMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Unsignal(elements[1]));
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'SIGNAL' command to the list of parsed commands.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addSignalMethod(String[] elements) {
        if (elements.length == 2 && checkLabel(elements[1])) {
            commands.add(new Signal(elements[1]));
        } else {
            throwSyntaxErrorException();
        }
    }

    /**
     * Adds a 'MOVE' command to the list of parsed commands.
     * If the line contains 'RANDOM' in the second element, it adds a 'MOVE RANDOM' command.
     *
     * @param elements The array containing the parsed command elements.
     */
    private void addMoveMethods(String[] elements) {
        if (elements[1].equals("RANDOM")) {
            if (elements.length == 7) {
                double[] args = toDoubleArray(2, elements);
                assert args != null;
                commands.add(new MoveRandom(new Position(args[0], args[1]), new Position(args[2], args[3]), args[4]));
            } else
                throwSyntaxErrorException();
        } else {
            if (elements.length == 4) {
                double[] args = toDoubleArray(1, elements);
                assert args != null;
                Position position = new Position(args[0], args[1]);
                double speed = args[2];
                commands.add(new Move(position, speed));
            } else
                throwSyntaxErrorException();
        }
    }
    /*###################################################*/

    /**
     * Converts a string array to a double array starting from a specified index.
     *
     * @param from     The starting index to convert to double.
     * @param elements The array containing the string elements to convert.
     * @return The converted double array.
     * @throws CommandException If the conversion fails due to a syntax error.
     */
    private double[] toDoubleArray(int from, String[] elements) throws CommandException {
        try {
            double[] result = new double[elements.length - from];
            for (int i = 0; i < result.length; i++) {
                result[i] = Double.parseDouble(elements[from + i]);
            }
            return result;
        } catch (NumberFormatException e) {
            throwSyntaxErrorException();
        }
        return null;
    }

    /**
     * Checks if a label is valid.
     *
     * @param label The label to check.
     * @return true if the label is valid, false otherwise.
     */
    private boolean checkLabel(String label) {
        Pattern p = Pattern.compile("^[A-Za-z\\d_]+$");
        Matcher m = p.matcher(label);
        return m.find();
    }

    /**
     * Throws a CommandException indicating a syntax error.
     *
     * @throws CommandException indicating a syntax error.
     */
    private void throwSyntaxErrorException() {
        throw new CommandException(String.format("Syntax error at line %d", linecounter));
    }

    /**
     * Retrieves the list of parsed commands.
     *
     * @return The list of parsed commands.
     */
    public List<Command> getCommands() {
        return this.commands;
    }
}

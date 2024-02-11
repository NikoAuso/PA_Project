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

/**
 * Instances of this class are used to parse a program from an external source.
 */
public final class CommandParser {
    private int linecounter = 0;
    private final List<Command> commands;

    public CommandParser() {
        this.commands = new ArrayList<>();
    }

    public synchronized void parseRobotProgram(File sourceFile) throws IOException, CommandException {
        parseRobotProgram(sourceFile.toPath());
    }

    public synchronized void parseRobotProgram(Path path) throws IOException, CommandException {
        parseRobotProgram(Files.readAllLines(path));
    }

    public synchronized void parseRobotProgram(String code) throws CommandException {
        parseRobotProgram(List.of(code.split("\n")));
    }

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
    private void addForeverMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new DoForever(linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addUntilMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Until(elements[1], linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }

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

    private void addDoneMethod(String[] elements) {
        if (elements.length == 1) {
            commands.add(new Done());
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addContinueMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Continue(Integer.parseInt(elements[1]), linecounter - 1));
        } else {
            throwSyntaxErrorException();
        }
    }
    /*###################################################*/


    // Movement commands
    /*###################################################*/
    private void addStopMethod(String[] elements) {
        if (elements.length == 1) {
            commands.add(new Stop());
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addFollowMethod(String[] elements) {
        if (elements.length == 4) {
            double[] args = toDoubleArray(2, elements);
            assert args != null;
            commands.add(new Follow(elements[1], args[0], args[1]));
        } else {
            throwSyntaxErrorException();
        }

    }

    private void addUnSignalMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Unsignal(elements[1]));
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addSignalMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Signal(elements[1]));
        } else {
            throwSyntaxErrorException();
        }
    }

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

    private void throwSyntaxErrorException() {
        throw new CommandException(String.format("Syntax error at line %d", linecounter));
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}

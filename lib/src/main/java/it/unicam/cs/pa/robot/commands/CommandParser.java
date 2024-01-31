package it.unicam.cs.pa.robot.commands;

import it.unicam.cs.pa.environment.Position;
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
            Optional<RobotCommand> oCommand = RobotCommand.selectCommand(line);
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

    private void addForeverMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new DoForever());//TODO: implementare
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addUntilMethod(String[] elements) {
        if (elements.length == 2) {
            commands.add(new Until());//TODO: implementare
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addRepeatMethod(String[] elements) {
        if (elements.length == 2) {
            try {
                commands.add(new Repeat());//TODO: implementare
            } catch (NumberFormatException e) {
                throwSyntaxErrorException();
            }
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addContinueMethod(String[] elements) {
        if (elements.length == 2) {
            try {
                commands.add(new Continue());//TODO: implementare
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

    private void addStopMethod(String[] elements) {
        if (elements.length == 1) {
            commands.add(new Stop());
        } else {
            throwSyntaxErrorException();
        }
    }

    private void addFollowMethod(String[] elements) {
        if (elements.length == 4) {
            commands.add(new Follow());//TODO: implementare
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
                Position position1 = new Position(args[0], args[1]);
                Position position2 = new Position(args[2], args[3]);
                double speed = args[4];
                commands.add(new MoveRandom(position1, position2, speed));
            } else
                throw new CommandException("Invalid input argument for instruction MOVE RANDOM!");
        } else {
            if (elements.length == 4) {
                double[] args = toDoubleArray(1, elements);
                assert args != null;
                Position position = new Position(args[0], args[1]);
                double speed = args[2];
                commands.add(new Move(position, speed));
            } else
                throw new CommandException("Invalid input argument for instruction MOVE!");
        }
    }

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
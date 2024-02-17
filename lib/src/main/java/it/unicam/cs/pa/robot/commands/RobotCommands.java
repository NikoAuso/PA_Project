package it.unicam.cs.pa.robot.commands;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * An enumeration representing various commands that can be executed by a robot.
 * Each command has a corresponding code used to identify it in command lines.
 */
public enum RobotCommands {
    MOVE("MOVE"),          // Command to move the robot
    SIGNAL("SIGNAL"),      // Command to signal a label
    UNSIGNAL("UNSIGNAL"),  // Command to stop signaling a label
    FOLLOW("FOLLOW"),      // Command to follow a robot with specified label
    STOP("STOP"),          // Command to stop the robot
    CONTINUE("CONTINUE"),  // Command to continue execution
    REPEAT("REPEAT"),      // Command to repeat a sequence of commands
    UNTIL("UNTIL"),        // Command to repeat until a condition is met
    FOREVER("DO FOREVER"), // Command to repeat forever
    DONE("DONE");          // Command to indicate the end of commands

    private final String code;  // The code used to identify the command in command lines

    /**
     * Constructs a RobotCommands enum with the given command code.
     *
     * @param code The code representing the command.
     */
    RobotCommands(String code) {
        this.code = code;
    }

    /**
     * Checks if the given line starts with the command code of this enum entry.
     *
     * @param line The line to check.
     * @return true if the line starts with the command code, false otherwise.
     */
    boolean isCommandOfLine(String line) {
        return line.startsWith(this.code);
    }

    /**
     * Selects the RobotCommands enum entry corresponding to the given command line.
     *
     * @param line The command line to parse and select the command from.
     * @return An Optional containing the selected RobotCommands enum entry, or empty if no matching command is found.
     */
    static Optional<RobotCommands> selectCommand(String line) {
        return Stream.of(RobotCommands.values())
                .filter(c -> c.isCommandOfLine(line.trim()))
                .findFirst();
    }
}

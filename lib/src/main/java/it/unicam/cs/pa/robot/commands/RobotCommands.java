package it.unicam.cs.pa.robot.commands;

import java.util.Optional;
import java.util.stream.Stream;

public enum RobotCommands {
    MOVE("MOVE"),
    SIGNAL("SIGNAL"),
    UNSIGNAL("UNSIGNAL"),
    FOLLOW("FOLLOW"),
    STOP("STOP"),
    CONTINUE("CONTINUE"),
    REPEAT("REPEAT"),
    UNTIL("UNTIL"),
    FOREVER("DO FOREVER"),
    DONE("DONE");

    private final String code;

    RobotCommands(String code) {
        this.code = code;
    }

    boolean isCommandOfLine(String line) {
        return line.startsWith(this.code);
    }

    static Optional<RobotCommands> selectCommand(String line) {
        return Stream.of(RobotCommands.values()).filter(c -> c.isCommandOfLine(line.trim())).findFirst();
    }
}

package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.CommandException;

public record Unsignal(String label) implements Command {
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.unisignal(label);
    }
}

package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.CommandException;

public class Signal implements Command {
    private final String label;

    public Signal(String label) {
        this.label = label;
    }

    @Override
    public void execute(Robot robot) throws CommandException {
        robot.signal(this.label);
    }
}

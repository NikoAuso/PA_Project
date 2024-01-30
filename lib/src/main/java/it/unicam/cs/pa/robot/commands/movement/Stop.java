package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.CommandException;

public class Stop implements Command {
    @Override
    public void execute(Robot robot) throws CommandException {
        robot.stop();
    }
}

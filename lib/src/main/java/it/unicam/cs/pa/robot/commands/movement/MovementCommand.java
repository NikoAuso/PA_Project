package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.robot.commands.Command;

public interface MovementCommand extends Command {
    @Override
    default Class<? extends Command> getType() {
        return MovementCommand.class;
    }
}

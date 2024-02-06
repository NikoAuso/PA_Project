package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.robot.commands.Command;

public interface LoopCommand extends Command {
    @Override
    default Class<? extends Command> getType() {
        return LoopCommand.class;
    }
}

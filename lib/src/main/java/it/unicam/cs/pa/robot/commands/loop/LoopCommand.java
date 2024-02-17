package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.robot.commands.Command;

/**
 * Represents a loop command that can be executed by a robot.
 * This interface extends the {@link Command} interface.
 */
public sealed interface LoopCommand extends Command permits Continue, DoForever, Done, Repeat, Until {
    @Override
    default Class<? extends Command> getType() {
        return LoopCommand.class;
    }
}

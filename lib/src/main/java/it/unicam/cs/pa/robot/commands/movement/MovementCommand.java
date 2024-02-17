package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.robot.commands.Command;

/**
 * Represents a movement command that can be executed by a robot.
 * This interface extends the {@link Command} interface.
 */
public sealed interface MovementCommand extends Command permits Follow, Move, MoveRandom, Signal, Stop, Unsignal {
    @Override
    default Class<? extends Command> getType() {
        return MovementCommand.class;
    }
}

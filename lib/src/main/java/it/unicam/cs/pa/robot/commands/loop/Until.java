package it.unicam.cs.pa.robot.commands.loop;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.Position;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.CommandException;

/**
 * Class representing the UNTIL command. This class create a loop for the robot that continue to execute a block
 * of commands until the robot is outside a shape with specified label.
 * It implements the {@link LoopCommand} interface.
 *
 * @param label         the label of the shape to check
 * @param startingIndex the command index in the list of commands
 * @see it.unicam.cs.pa.environment.shapes.Shape#contains(Position)
 */
public record Until(String label, int startingIndex) implements LoopCommand {
    /**
     * Executes the UNTIL loop command, repeating a block of commands until the robot is inside a shape with specified label.
     *
     * @param environment The environment in which the robot operates.
     * @param robot       The robot that executes the command.
     * @throws CommandException if an error occurs while executing the command.
     */
    @Override
    public void execute(Environment environment, Robot robot) throws CommandException {
        robot.pushStartingLoopIndex(startingIndex);
        if (environment.getShapes().stream()
                .filter(s -> s.getLabel().equals(this.label.toUpperCase()))
                .anyMatch(s -> s.contains(robot.position()))) {
            robot.pushLoopCount(1, startingIndex);
            robot.decreaseLoopCount();
        } else
            robot.increaseCurrentCommandIndex();
    }
}

package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.loop.LoopCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * The SimulationController class manages the simulation logic, including the execution of commands
 * for each robot in the environment.
 */
public class SimulationController {
    private final Environment environment;                                  // The environment where robots operate
    private final List<Command> program;                                    // The program containing commands to be executed by robots
    private final List<SimulationListener> listeners = new ArrayList<>();   // Listeners for simulation state changes

    /**
     * Constructs a new SimulationController instance with a new environment and an empty program.
     */
    public SimulationController() {
        this.environment = new Environment();
        this.program = new ArrayList<>();
    }

    /**
     * Gets the environment managed by this simulation controller.
     *
     * @return The environment.
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Gets the program containing commands to be executed by robots.
     *
     * @return The program.
     */
    public List<Command> getProgram() {
        return program;
    }

    /**
     * Executes one step of the simulation, which involves executing commands for each robot in the environment.
     * Notifies simulation state changes to registered listeners after each step.
     */
    public void step() {
        environment.getRobots().parallelStream()
                //.filter(Robot::canMove)
                .forEach(r -> {
                    if (r.currentCommandIndex() < program.size()) {
                        Command c = program.get(r.currentCommandIndex());
                        c.execute(environment, r);
                        if (r.canMove())
                            r.move();
                        if (c.getType() != LoopCommand.class)
                            r.increaseCurrentCommandIndex();
                    }
                });
        notifySimulationStateChanged();
    }

    /**
     * Adds a simulation listener to be notified of simulation state changes.
     *
     * @param listener The listener to add.
     */
    public void addSimulationListener(SimulationListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered simulation listeners about a simulation state change.
     */
    private void notifySimulationStateChanged() {
        for (SimulationListener listener : listeners) {
            listener.simulationStateChanged();
        }
    }
}
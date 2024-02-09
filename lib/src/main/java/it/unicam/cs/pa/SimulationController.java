package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.Robot;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.loop.LoopCommand;

import java.util.ArrayList;
import java.util.List;

public class SimulationController {
    private final Environment environment;
    private final List<Command> program;
    private final List<SimulationListener> listeners = new ArrayList<>();

    public SimulationController() {
        this.environment = new Environment();
        this.program = new ArrayList<>();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<Command> getProgram() {
        return program;
    }

    public void addSimulationListener(SimulationListener listener) {
        listeners.add(listener);
    }

    private void notifySimulationStateChanged() {
        for (SimulationListener listener : listeners) {
            listener.simulationStateChanged();
        }
    }

    public void step() {
        environment.getRobots().parallelStream()
                .filter(Robot::canMove)
                .forEach(r -> {
                    if(r.getCurrentCommandIndex() < this.program.size()) {
                        Command c = program.get(r.getCurrentCommandIndex());
                        c.execute(environment, r);
                        System.out.println(r.direction());
                        if (r.canMove())
                            r.move();
                        if (c.getType() != LoopCommand.class)
                            r.increaseCurrentCommandIndex();
                    }
                });
        notifySimulationStateChanged();
    }
}
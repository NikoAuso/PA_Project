package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.commands.Command;
import it.unicam.cs.pa.robot.commands.loop.LoopCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationController implements Simulator {
    private final Environment environment;
    private final List<Command> program;
    private ExecutorService executorService;
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

    public void setUpSimulation() {
        executorService = Executors.newFixedThreadPool(this.environment.getRobots().size());
    }

    public void addSimulationListener(SimulationListener listener) {
        listeners.add(listener);
    }

    private void notifySimulationStateChanged() {
        for (SimulationListener listener : listeners) {
            listener.simulationStateChanged();
        }
    }

    @Override
    public void simulate(double dt, double time) {
        try {
            double currentTime = 0;
            while (currentTime < time) {
                this.step();
                currentTime += dt;
            }
        } finally {
            interrupt();
        }
    }

    public void step() {
        environment.getRobots().parallelStream().forEach(r -> {
            Command c = program.get(r.getCurrentCommandIndex());
            c.execute(environment, r);
            System.out.println(r.getDirection());
            if (r.canMove())
                r.move();
            if (c.getType() != LoopCommand.class)
                r.increaseCurrentCommandIndex();
        });
        notifySimulationStateChanged();
    }

    public void interrupt() {
        executorService.shutdown();
    }
}
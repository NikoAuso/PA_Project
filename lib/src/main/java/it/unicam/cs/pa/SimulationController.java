package it.unicam.cs.pa;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.robot.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class SimulationController implements Simulator {
    private Environment environment;
    private List<Command> program;

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

    @Override
    public void simulate(double dt, double time) {

    }
}
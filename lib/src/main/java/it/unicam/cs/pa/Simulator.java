package it.unicam.cs.pa;

/**
 * Interface representing a simulator for the robot swarm simulation system.
 */
public interface Simulator {
    /**
     * Simulates the behavior of the robot swarm system for a specified duration of time.
     *
     * @param dt   The time step for each simulation iteration, in seconds.
     * @param time The total duration of the simulation, in seconds.
     */
    void simulate(double dt, double time);
}

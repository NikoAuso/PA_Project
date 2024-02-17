package it.unicam.cs.pa;

/**
 * The SimulationListener interface defines a contract for objects that should be notified
 * when the state of a simulation changes.
 * This interface allows implementing classes to receive notifications about changes in the simulation state.
 */
public interface SimulationListener {
    /**
     * Notifies the listener that the state of the simulation has changed.
     * Implementing classes can react to this notification by performing actions related to the simulation state change.
     */
    void simulationStateChanged();
}

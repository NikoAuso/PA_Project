package it.unicam.cs.pa.entity.shapes;

import it.unicam.cs.pa.entity.Position;

import java.util.List;

public interface Shape {
    /**
     * Get the center position of the shape as
     *
     * @return center position of the shape
     */
    Position getCenter();

    /**
     * Check if a given position is contained in the shape
     *
     * @param toCheckposition the position to check
     * @return true if the position is contained, false otherwise
     */
    boolean isContained(Position toCheckposition);

    /**
     * Generate a list of random position to spawn in the environment
     *
     * @param total the number of position to spawn
     * @return list of random positions
     */
    List<Position> generateRandomPositions(int total);
}

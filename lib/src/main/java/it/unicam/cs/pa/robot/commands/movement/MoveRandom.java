package it.unicam.cs.pa.robot.commands.movement;

import it.unicam.cs.pa.environment.Position;

public class MoveRandom extends Move {
    public MoveRandom(double x1, double x2, double y1, double y2,double speed) {
        super(Position.random(new Position(x1, y1), new Position(x2, y2)), speed);
    }

    public MoveRandom(Position position1, Position position2,double speed) {
        super(Position.random(position1, position2), speed);
    }
}

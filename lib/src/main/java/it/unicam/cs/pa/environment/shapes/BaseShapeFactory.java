package it.unicam.cs.pa.environment.shapes;

import it.unicam.cs.pa.environment.Position;

public class BaseShapeFactory implements ShapeFactory {
    @Override
    public Shape createShape(String label, ShapeType shapeType, double[] args) {
        Position center = new Position(args[0], args[1]);
        return switch (shapeType) {
            case CIRCLE -> createCircle(center, args, label);
            case RECTANGLE -> createRectangle(center, args, label);
        };
    }

    public Shape createCircle(Position center, double[] args, String label){
        return new Circle(center, args[2], label);
    }

    public Shape createRectangle(Position center, double[] args, String label){
        return new Rectangle(center, args[2], args[3], label);
    }
}

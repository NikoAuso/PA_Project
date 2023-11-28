package it.unicam.cs.pa.entity.shapes;

public final class Rectangle implements Shape{

    @Override
    public double[] getProperties() {
        return new double[0];
    }

    @Override
    public boolean isContained() {
        return false;
    }
}

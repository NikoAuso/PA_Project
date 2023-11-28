package it.unicam.cs.pa.entity.shapes;

import it.unicam.cs.pa.entity.Position;

public sealed interface Shape permits Circle, Rectangle{
    Position getProperties();

    boolean isContained();


}

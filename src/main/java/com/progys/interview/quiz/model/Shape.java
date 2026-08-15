package com.progys.interview.quiz.model;

/**
 * Defines a shape.
 * 
 * @author progys
 */
public sealed interface Shape permits Circle, Triangle, Donut {
    double getArea();

    boolean inShape(Point point);

    BoundingBox getBounds();
}

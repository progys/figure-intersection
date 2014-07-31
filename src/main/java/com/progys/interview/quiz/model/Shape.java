package com.progys.interview.quiz.model;

import com.progys.interview.quiz.parser.NamedObject;

/**
 * Defines a shape.
 * 
 * @author progys
 */
public interface Shape extends NamedObject {
    double getArea();

    boolean inShape(Point point);
}

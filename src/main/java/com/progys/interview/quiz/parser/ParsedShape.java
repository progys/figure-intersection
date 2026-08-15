package com.progys.interview.quiz.parser;

import com.progys.interview.quiz.model.Shape;

/**
 * A parsed shape definition.
 * 
 * @author progys
 */
public record ParsedShape(Shape shape) implements ParsedObject {
}

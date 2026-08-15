package com.progys.interview.quiz.parser;

import com.progys.interview.quiz.model.Point;

/**
 * A parsed point query.
 * 
 * @author progys
 */
public record ParsedPoint(Point point) implements ParsedObject {
}

package com.progys.interview.quiz.exceptions;

import com.progys.interview.quiz.model.ShapeNames;

/**
 * Defines a parse exception.
 * 
 * @author progys
 */
public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(ShapeNames shape, Exception e) {
        super("Invalid arguments provided for " + shape + " : " + e.getMessage());
    }
}

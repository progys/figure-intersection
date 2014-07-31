package com.progys.interview.quiz.model;

import com.progys.interview.quiz.exceptions.ParseException;

/**
 * Defines known shape names.
 * 
 * @author progys
 */
public enum ShapeNames {
    circle, triangle, donut;

    public static ShapeNames toName(String shape) {
        try {
            return ShapeNames.valueOf(shape);
        } catch (Exception e) {
            throw new ParseException("Unrecognized shape name: " + shape);
        }
    }
}

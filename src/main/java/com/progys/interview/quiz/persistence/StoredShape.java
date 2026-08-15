package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Shape;

/**
 * A shape combined with its database identifier.
 * 
 * @author progys
 */
public record StoredShape(Long id, Shape shape) {
    @Override
    public String toString() {
        return String.format("=> Shape %s: %s", id, shape);
    }
}

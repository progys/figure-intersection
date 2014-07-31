package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Shape;

import java.util.Collection;

/**
 * @author progys
 */
public interface Store {
    public Shape put(Shape shape);

    public void clear();

    public Collection<Shape> getAll();

    public void close();
}

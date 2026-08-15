package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Shape;

import java.util.Collection;

/**
 * @author progys
 */
public interface Store {
    StoredShape put(Shape shape);

    void clear();

    Collection<StoredShape> getAll();

    void close();
}

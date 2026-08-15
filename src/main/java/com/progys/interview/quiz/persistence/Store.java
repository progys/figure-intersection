package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;

import java.util.Collection;

/**
 * @author progys
 */
public interface Store {
    StoredShape put(Shape shape);

    void clear();

    Collection<StoredShape> getAll();

    Collection<StoredShape> queryContaining(Point point);

    void close();
}

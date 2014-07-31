package com.progys.interview.quiz.model;

import com.progys.interview.quiz.parser.CommandNames;
import com.progys.interview.quiz.parser.NamedObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Shape for storing to DB.
 * 
 * @author progys
 */
@Entity
public class AbstractEntity implements Shape, NamedObject, Serializable, Comparable<AbstractEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Shape shape;

    public AbstractEntity() {
    }

    public AbstractEntity(Shape shape) {
        this.shape = shape;
    }

    public Long getId() {
        return id;
    }

    public void setId(Object id) {
        if (id instanceof Long) {
            this.id = (Long) id;
        }
    }

    public double getArea() {
        return shape.getArea();
    }

    @Override
    public boolean inShape(Point point) {
        return shape.inShape(point);
    }

    @Override
    public CommandNames getName() {
        return CommandNames.shape;
    }

    @Override
    public String toString() {
        return String.format("=> Shape %s: %s", id, shape);
    }

    @Override
    public int compareTo(AbstractEntity o) {
        return this.getId().compareTo(o.getId());
    }
}

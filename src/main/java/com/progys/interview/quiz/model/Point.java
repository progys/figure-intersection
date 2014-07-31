package com.progys.interview.quiz.model;

import com.progys.interview.quiz.parser.CommandNames;
import com.progys.interview.quiz.parser.NamedObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Defines a point.
 * 
 * @author progys
 */
@Entity
public class Point implements NamedObject {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    public double x;
    public double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    @Override
    public CommandNames getName() {
        return CommandNames.point;
    }
}

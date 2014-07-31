package com.progys.interview.quiz.model;

import com.google.common.base.Preconditions;
import com.progys.interview.quiz.parser.CommandNames;
import com.progys.interview.quiz.parser.NamedObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

/**
 * Defines a circle.
 * 
 * @author progys
 */
@Entity
public class Circle implements Shape, NamedObject {
    @OneToOne(cascade = CascadeType.ALL)
    private Point center;

    private double radius;
    private double area;

    public Circle() {
    }

    public Circle(Point center, double radius) {
        Preconditions.checkArgument(radius > 0, "Circle radius should be positive number");

        this.center = center;
        this.radius = radius;
        this.area = PI * pow(radius, 2);
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea() {
        return area;
    }

    @Override
    public boolean inShape(Point point) {
        return pow(point.x - center.x, 2) + pow(point.y - center.y, 2) < pow(radius, 2);
    }

    @Override
    public String toString() {
        return String.format("circle with centre at (%s, %s) and radius %s", center.x, center.y,
                radius);
    }

    @Override
    public CommandNames getName() {
        return CommandNames.shape;
    }
}

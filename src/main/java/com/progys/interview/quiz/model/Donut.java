package com.progys.interview.quiz.model;

import com.google.common.base.Preconditions;
import com.progys.interview.quiz.parser.CommandNames;
import com.progys.interview.quiz.parser.NamedObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;

/**
 * Defines a donut.
 * 
 * @author progys
 */
@Entity
public class Donut implements Shape, NamedObject {
    @OneToOne(cascade = CascadeType.ALL)
    private Circle innerCircle;

    @OneToOne(cascade = CascadeType.ALL)
    private Circle outerCircle;

    @OneToOne(cascade = CascadeType.ALL)
    private Point center;

    private double area;

    public Donut() {
    }

    public Donut(double innerRadius, double outerRadius, Point center) {
        Preconditions.checkArgument(innerRadius != outerRadius,
                "Donut inner radius is equal to outer radius");

        this.innerCircle = new Circle(center, min(innerRadius, outerRadius));
        this.outerCircle = new Circle(center, max(innerRadius, outerRadius));
        this.center = center;
        this.area = outerCircle.getArea() - innerCircle.getArea();
    }

    public Circle getInnerCircle() {
        return innerCircle;
    }

    public Circle getOuterCircle() {
        return outerCircle;
    }

    public Point getCenter() {
        return center;
    }

    public double getArea() {
        return area;
    }

    @Override
    public boolean inShape(Point point) {
        return outerCircle.inShape(point) && !innerCircle.inShape(point);
    }

    @Override
    public String toString() {
        return String.format(
                "donut with centre at (%s, %s) with inner radius %s and outer radius %s ",
                center.x, center.y, innerCircle.getRadius(), outerCircle.getRadius());
    }

    @Override
    public CommandNames getName() {
        return CommandNames.shape;
    }
}

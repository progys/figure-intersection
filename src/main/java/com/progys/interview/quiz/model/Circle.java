package com.progys.interview.quiz.model;

import com.google.common.base.Preconditions;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

/**
 * Defines a circle.
 * 
 * @author progys
 */
public final class Circle implements Shape {
    private final Point center;
    private final double radius;
    private final double area;

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
}

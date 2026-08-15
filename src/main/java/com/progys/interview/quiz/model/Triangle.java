package com.progys.interview.quiz.model;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;

/**
 * Defines a triangle.
 * 
 * @author progys
 */
public final class Triangle implements Shape {
    private final Point v0;
    private final Point v1;
    private final Point v2;

    private final double area;

    public Triangle(Point v0, Point v1, Point v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.area = (-v1.y * v2.x + v0.y * (-v1.x + v2.x) + v0.x * (v1.y - v2.y) + v1.x * v2.y) / 2;
    }

    public Point getV0() {
        return v0;
    }

    public Point getV1() {
        return v1;
    }

    public Point getV2() {
        return v2;
    }

    public double getArea() {
        return abs(area);
    }

    @Override
    public boolean inShape(Point point) {
        double d = 1 / (2 * area);
        double s = d
                * (v0.y * v2.x - v0.x * v2.y + (v2.y - v0.y) * point.x + (v0.x - v2.x) * point.y);
        double t = d
                * (v0.x * v1.y - v0.y * v1.x + (v0.y - v1.y) * point.x + (v1.x - v0.x) * point.y);
        return s > 0 && t > 0 && (s + t) < 1;
    }

    @Override
    public BoundingBox getBounds() {
        return new BoundingBox(
                min(min(v0.x, v1.x), v2.x), max(max(v0.x, v1.x), v2.x),
                min(min(v0.y, v1.y), v2.y), max(max(v0.y, v1.y), v2.y));
    }

    @Override
    public String toString() {
        return String.format("triangle at v0=(%s, %s), v1=(%s, %s), v2=(%s, %s)", v0.x, v0.y, v1.x,
                v1.y, v2.x, v2.y);
    }
}

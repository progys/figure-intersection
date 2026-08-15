package com.progys.interview.quiz.model;

/**
 * Axis-aligned bounding box of a shape.
 *
 * @author progys
 */
public record BoundingBox(double minX, double maxX, double minY, double maxY) {
    public boolean contains(Point point) {
        return point.x >= minX && point.x <= maxX && point.y >= minY && point.y <= maxY;
    }
}

package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Donut;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.model.Triangle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * JPA entity that stores shapes as flat primitive fields, keeping the persistence model
 * separate from the domain model.
 *
 * @author progys
 */
@Entity
public class ShapeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double ax;
    private double ay;
    private double bx;
    private double by;
    private double cx;
    private double cy;
    private double radius;
    private double innerRadius;
    private double outerRadius;

    public ShapeEntity() {
    }

    public ShapeEntity(Shape shape) {
        switch (shape) {
            case Circle circle -> {
                type = "circle";
                ax = circle.getCenter().getX();
                ay = circle.getCenter().getY();
                radius = circle.getRadius();
            }
            case Triangle triangle -> {
                type = "triangle";
                ax = triangle.getV0().getX();
                ay = triangle.getV0().getY();
                bx = triangle.getV1().getX();
                by = triangle.getV1().getY();
                cx = triangle.getV2().getX();
                cy = triangle.getV2().getY();
            }
            case Donut donut -> {
                type = "donut";
                ax = donut.getCenter().getX();
                ay = donut.getCenter().getY();
                innerRadius = donut.getInnerCircle().getRadius();
                outerRadius = donut.getOuterCircle().getRadius();
            }
            case null -> throw new IllegalArgumentException("Cannot store a null shape");
            default -> throw new IllegalArgumentException(
                    "Unsupported shape type: " + shape.getClass().getName());
        }
    }

    public Long getId() {
        return id;
    }

    public Shape toShape() {
        return switch (type) {
            case "circle" -> new Circle(new Point(ax, ay), radius);
            case "triangle" ->
                new Triangle(new Point(ax, ay), new Point(bx, by), new Point(cx, cy));
            case "donut" -> new Donut(innerRadius, outerRadius, new Point(ax, ay));
            default -> throw new IllegalStateException("Unknown shape type: " + type);
        };
    }
}

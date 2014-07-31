package com.progys.interview.quiz.parser;

import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.exceptions.ParseException;
import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Donut;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.model.ShapeNames;
import com.progys.interview.quiz.model.Triangle;

import javax.inject.Inject;
import java.util.Scanner;

/**
 * Parses a shape.
 * 
 * @author progys
 */
public class ShapeParser implements Parser<NamedObject> {
    private final Scanner scanner;
    private final Parser<Point> pointParser;

    @Inject
    public ShapeParser(@Assisted Scanner scanner, @Assisted Parser<Point> pointParser) {
        this.scanner = scanner;
        this.pointParser = pointParser;
    }

    public Shape parse() throws ParseException {
        ShapeNames shapeName = ShapeNames.toName(scanner.next());
        Shape shape = null;

        switch (shapeName) {
        case circle:
            shape = parseCircle();
            break;
        case triangle:
            shape = parseTriangle();
            break;
        case donut:
            shape = parseDonut();
        }
        return shape;
    }

    private Shape parseDonut() {
        try {
            Point center = pointParser.parse();
            double innerRadius = scanner.nextDouble();
            double outerRadius = scanner.nextDouble();
            return new Donut(innerRadius, outerRadius, center);
        } catch (Exception e) {
            throw new ParseException(ShapeNames.donut, e);
        }
    }

    private Shape parseTriangle() {
        try {
            Point v0 = pointParser.parse();
            Point v1 = pointParser.parse();
            Point v2 = pointParser.parse();
            return new Triangle(v0, v1, v2);
        } catch (Exception e) {
            throw new ParseException(ShapeNames.triangle, e);
        }
    }

    private Shape parseCircle() {
        try {
            Point center = pointParser.parse();
            double radius = scanner.nextDouble();
            return new Circle(center, radius);
        } catch (Exception e) {
            throw new ParseException(ShapeNames.circle.circle, e);
        }
    }
}

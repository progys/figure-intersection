package com.progys.interview.quiz.parser;

import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.exceptions.ParseException;
import com.progys.interview.quiz.model.Point;

import javax.inject.Inject;
import java.util.Scanner;

/**
 * Parses a point.
 * 
 * @author progys
 */
public class PointParser implements Parser<NamedObject> {
    private final Scanner scanner;

    @Inject
    public PointParser(@Assisted Scanner scanner) {
        this.scanner = scanner;
    }

    public Point parse() {
        try {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            return new Point(x, y);
        } catch (Exception e) {
            throw new ParseException("Invalid arguments provided for point.");
        }
    }
}

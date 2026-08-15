package com.progys.interview.quiz.parser;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;

import java.util.Scanner;

/**
 * Parser factory.
 *
 * @author progys
 */
public interface ConcreteParserFactory {
    @Named("point")
    Parser<Point> getPointParser(@Assisted Scanner scanner);

    @Named("shape")
    Parser<Shape> getShapeParser(@Assisted Scanner scanner, @Assisted Parser<Point> pointParser);

    @Named("command")
    Parser<ParsedAction> getCommandParser(@Assisted String command);
}

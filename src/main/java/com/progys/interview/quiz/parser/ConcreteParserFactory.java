package com.progys.interview.quiz.parser;

import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.Point;

import javax.inject.Named;
import java.util.Scanner;

/**
 * Parser factory.
 * @author progys
 */
public interface ConcreteParserFactory {
    @Named("point")
    Parser getPointParser(@Assisted Scanner scanner);

    @Named("shape")
    Parser getShapeParser(@Assisted Scanner scanner, @Assisted Parser<Point> pointParser);

    @Named("command")
    Parser getCommandParser(@Assisted String command);
}

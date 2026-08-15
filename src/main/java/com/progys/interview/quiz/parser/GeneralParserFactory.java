package com.progys.interview.quiz.parser;

import com.google.inject.Inject;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;

import java.util.Scanner;

/**
 * Creates parser from given string.
 * 
 * @author progys
 */
public class GeneralParserFactory implements ParserFactory {
    private final ConcreteParserFactory concreteParserFactory;

    @Inject
    public GeneralParserFactory(ConcreteParserFactory concreteParserFactory) {
        this.concreteParserFactory = concreteParserFactory;
    }

    @Override
    public Parser<ParsedObject> create(String command) {
        if (isActionCommand(command)) {
            return () -> concreteParserFactory.getCommandParser(command).parse();
        }
        return () -> parseGeometry(command);
    }

    private boolean isActionCommand(String command) {
        return switch (command) {
            case "exit", "help", "list", "clear", "" -> true;
            default -> false;
        };
    }

    private ParsedObject parseGeometry(String command) {
        Scanner scanner = new Scanner(command);
        if (scanner.hasNextDouble()) {
            Point point = concreteParserFactory.getPointParser(scanner).parse();
            return new ParsedPoint(point);
        }
        Parser<Point> pointParser = concreteParserFactory.getPointParser(scanner);
        Shape shape = concreteParserFactory.getShapeParser(scanner, pointParser).parse();
        return new ParsedShape(shape);
    }
}

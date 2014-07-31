package com.progys.interview.quiz.parser;

import com.google.inject.Inject;

import java.util.Scanner;

/**
 * Creates parser from given string.
 * 
 * @author progys
 */
public class GeneralParserFactory implements ParserFactory {
    private ConcreteParserFactory concreteParserFactory;

    @Inject
    public GeneralParserFactory(ConcreteParserFactory concreteParserFactory) {
        this.concreteParserFactory = concreteParserFactory;
    }

    public Parser<NamedObject> create(String command) {
        switch (command) {
        case "exit":
        case "help":
        case "list":
        case "":
        case "clear":
            return concreteParserFactory.getCommandParser(command);
        default:
            return createGeometryParser(command);
        }
    }

    private Parser<NamedObject> createGeometryParser(String command) {
        Scanner scanner = new Scanner(command);
        if (scanner.hasNextDouble()) {
            return concreteParserFactory.getPointParser(scanner);
        } else {
            return concreteParserFactory.getShapeParser(scanner,
                    concreteParserFactory.getPointParser(scanner));
        }
    }
}

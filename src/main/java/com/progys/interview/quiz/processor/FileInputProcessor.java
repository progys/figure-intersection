package com.progys.interview.quiz.processor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.commands.CommandFactory;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.parser.ConcreteParserFactory;
import com.progys.interview.quiz.parser.ParsedShape;
import com.progys.interview.quiz.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInputProcessor implements InputProcessor {
    private final File file;
    private final CommandFactory commandFactory;
    private final ConcreteParserFactory concreteParserFactory;

    @Inject
    FileInputProcessor(CommandFactory commandFactory, @Assisted File input,
                       ConcreteParserFactory concreteParserFactory) {
        this.file = input;
        this.commandFactory = commandFactory;
        this.concreteParserFactory = concreteParserFactory;
    }

    public void process() {
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Reading provided input file: " + file.getAbsolutePath());
            while (scanner.hasNextLine()) {
                processLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath() + "\n");
        }
    }

    private void processLine(String line) {
        try (Scanner lineScanner = new Scanner(line)) {
            Parser<Point> pointParser = concreteParserFactory.getPointParser(lineScanner);
            Parser<Shape> shapeParser = concreteParserFactory.getShapeParser(lineScanner,
                    pointParser);
            commandFactory.getCommand(new ParsedShape(shapeParser.parse()), true).execute();
        } catch (Exception e) {
            System.out.println("Exception while reading input from file: " + e.getMessage());
        }
    }
}

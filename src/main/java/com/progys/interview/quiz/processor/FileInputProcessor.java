package com.progys.interview.quiz.processor;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.commands.CommandFactory;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.parser.ConcreteParserFactory;
import com.progys.interview.quiz.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads input from file into memory.
 * 
 * @author progys
 */
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
        try {
            System.out.println("Reading provided input file: " + file.getAbsolutePath());
            processLines();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath() + "\n");
        }
    }

    private void processLines() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                processLine(scanner);
            }
        }
    }

    private void processLine(Scanner fileScanner) {
        try (Scanner lineScanner = new Scanner(fileScanner.nextLine())) {
            Parser<Shape> parser = concreteParserFactory.getShapeParser(lineScanner,
                    concreteParserFactory.getPointParser(lineScanner));
            commandFactory.getCommand(parser.parse(), true).execute();
        } catch (Exception e) {
            System.out.println("Exception while reading input from file: " + e.getMessage());
        }
    }
}

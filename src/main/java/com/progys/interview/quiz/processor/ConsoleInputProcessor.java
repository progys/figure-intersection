package com.progys.interview.quiz.processor;

import com.progys.interview.quiz.commands.CommandFactory;
import com.progys.interview.quiz.parser.NamedObject;
import com.progys.interview.quiz.parser.Parser;
import com.progys.interview.quiz.parser.ParserFactory;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Reads input from console into memory.
 * 
 * @author progys
 */
public class ConsoleInputProcessor implements InputProcessor {
    private InputStream console;
    private CommandFactory commandFactory;
    private ParserFactory parserFactory;

    @Inject
    ConsoleInputProcessor(CommandFactory commandFactory, InputStream console,
            ParserFactory parserFactory) {
        this.commandFactory = commandFactory;
        this.console = console;
        this.parserFactory = parserFactory;
    }

    public void process() {
        System.out.println("Please enter the command or 'help' for command list:");

        try (Scanner scanner = new Scanner(console)) {
            readLines(scanner);
        }
    }

    private void readLines(Scanner scanner) {
        while (scanner.hasNextLine()) {
            try {
                String command = scanner.nextLine();
                Parser<NamedObject> parser = parserFactory.create(command);
                commandFactory.getCommand(parser.parse(), false).execute();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;

/**
 * Defines exit action.
 * 
 * @author progys
 */
public class ExitCommand extends AbstractCommand {
    private final Store persistence;

    @Inject
    ExitCommand(Store persistence, PrintStream output) {
        super(output);
        this.persistence = persistence;
    }

    @Override
    public void process() {
        output.println("Exiting...");
        persistence.close();
        System.exit(0);
    }
}

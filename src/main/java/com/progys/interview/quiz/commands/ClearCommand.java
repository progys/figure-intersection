package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;

/**
 * @author progys
 */
public class ClearCommand extends AbstractCommand {
    private final Store persistence;

    @Inject
    ClearCommand(Store persistence, PrintStream printStream) {
        super(printStream);
        this.persistence = persistence;
    }

    @Override
    protected void process() {
        output.print("Clearing shapes...");
        persistence.clear();
        output.print("DONE.");
    }
}

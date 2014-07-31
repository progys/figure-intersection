package com.progys.interview.quiz.commands;

import javax.inject.Inject;
import java.io.PrintStream;

/**
 * Defines abstract action execution template.
 * 
 * @author progys
 */
public abstract class AbstractCommand implements Command {
    final PrintStream output;

    @Inject
    AbstractCommand(PrintStream output) {
        this.output = output;
    }

    protected void printSeparator() {
        output.println("\n----------------------------------------------------------------- ");
    }

    @Override
    public void execute() {
        printSeparator();
        process();
        printSeparator();
    }

    protected abstract void process();
}

package com.progys.interview.quiz.commands;

import com.google.inject.Inject;

import java.io.PrintStream;

/**
 * Defines an empty action.
 *
 * @author progys
 */
public class EmptyCommand extends AbstractCommand {

    @Inject
    EmptyCommand(PrintStream output) {
        super(output);
    }

    @Override
    public void process() {
        output.println("Not recognized command. Please enter 'help' for command list.");
    }
}

package com.progys.interview.quiz.commands;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.PrintStream;

/**
 * Defines an empty action.
 * 
 * @author progys
 */
@Singleton
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

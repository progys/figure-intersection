package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;

/**
 * Defines action for given shape.
 * 
 * @author progys
 */
public class ShapeCommand extends AbstractCommand {
    private final Store persistence;
    private final Shape shape;
    private final boolean silent;

    @Inject
    public ShapeCommand(Store persistence, @Assisted Shape shape, @Assisted boolean silent,
            PrintStream output) {
        super(output);
        this.persistence = persistence;
        this.shape = shape;
        this.silent = silent;
    }

    @Override
    public void process() {
        Shape entity = persistence.put(shape);
        if (!silent) {
            output.println(entity);
        }
    }

    @Override
    protected void printSeparator() {
        if (!silent)
            super.printSeparator();
    }
}

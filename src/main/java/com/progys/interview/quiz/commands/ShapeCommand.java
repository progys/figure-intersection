package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.persistence.StoredShape;
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
    private final ShapeOutputMode outputMode;

    @Inject
    public ShapeCommand(Store persistence, @Assisted ShapeOutputMode outputMode,
            @Assisted Shape shape, PrintStream output) {
        super(output);
        this.persistence = persistence;
        this.shape = shape;
        this.outputMode = outputMode;
    }

    @Override
    public void process() {
        StoredShape storedShape = persistence.put(shape);
        if (outputMode == ShapeOutputMode.VERBOSE) {
            output.println(storedShape);
        }
    }

    @Override
    protected void printSeparator() {
        if (outputMode == ShapeOutputMode.VERBOSE) {
            super.printSeparator();
        }
    }
}

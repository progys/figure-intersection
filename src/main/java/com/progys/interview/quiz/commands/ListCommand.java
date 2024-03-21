package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Defines action which lists existing shapes.
 * 
 * @author progys
 */
public class ListCommand extends AbstractCommand {
    private final Store persistence;

    @Inject
    ListCommand(Store persistence, PrintStream output) {
        super(output);
        this.persistence = persistence;
    }

    @Override
    public void process() {
        output.println("List of currently existing shapes: ");
        Collection<Shape> shapes = persistence.getAll();
        for (Shape shape : shapes) {
            output.printf("%s;%n", shape);
        }

        output.printf("Total shapes count: %s%n", shapes.size());
    }
}

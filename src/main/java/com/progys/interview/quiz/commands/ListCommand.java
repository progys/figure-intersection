package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.persistence.Store;

import javax.inject.Inject;
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
        output.println(String.format("List of currently existing shapes: "));
        Collection<Shape> shapes = persistence.getAll();
        for (Shape shape : shapes) {
            output.println(String.format("%s;", shape));
        }

        output.println(String.format("Total shapes count: %s", shapes.size()));
    }
}

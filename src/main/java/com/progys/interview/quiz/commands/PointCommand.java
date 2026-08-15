package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.persistence.StoredShape;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;
import java.util.List;

/**
 * Defines action for point input.
 * 
 * @author progys
 */
public class PointCommand extends AbstractCommand {
    private final Point point;
    private final Store persistence;

    @Inject
    PointCommand(@Assisted Point point, Store persistence, PrintStream output) {
        super(output);
        this.point = point;
        this.persistence = persistence;
    }

    @Override
    public void process() {
        List<StoredShape> containing = persistence.getAll().parallelStream()
                .filter(stored -> stored.shape().inShape(point))
                .toList();
        printShapes(containing);
    }

    private void printShapes(List<StoredShape> containing) {
        output.println(String.format("Shape list containing point %s:", point));

        double totalArea = 0;
        for (StoredShape stored : containing) {
            totalArea += stored.shape().getArea();
            output.println(String.format("%s; Shape area: %.2f", stored,
                    stored.shape().getArea()));
        }

        printTotalSurfaceArea(totalArea, containing.size());
    }

    private void printTotalSurfaceArea(double totalArea, long shapesCount) {
        if (shapesCount > 0) {
            output.println(String.format(
                    "Found %s shapes containing point %s. Surface area combined:  %.4f",
                    shapesCount, point, totalArea));
        } else {
            noShapesContainingPoint();
        }
    }

    private void noShapesContainingPoint() {
        output.println("No shapes found. Total surface area: 0");
    }
}

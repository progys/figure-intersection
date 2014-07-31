package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.persistence.Store;

import java.io.PrintStream;
import java.util.Collection;
import java.util.function.Consumer;

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
        Collection<Shape> shapes = persistence.getAll();
        printShapes(shapes);
    }

    private void printShapes(Collection<Shape> shapes) {
        output.println(String.format("Shape list containing point %s:", point));

        AreaConsumer areaConsumer = shapes.parallelStream().filter(shape -> shape.inShape(point)).collect(AreaConsumer::new, AreaConsumer::accept, AreaConsumer::combine);

        printTotalSurfaceArea(areaConsumer.totalArea, areaConsumer.count);
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

    private class AreaConsumer implements Consumer<Shape> {
        private double totalArea = 0;
        private int count = 0;

        public void accept(Shape shape) {
            totalArea += shape.getArea();
            count++;
            output.println(String.format("%s; Shape area: %.2f", shape, shape.getArea()));
        }

        public void combine(AreaConsumer other) {
            totalArea += other.totalArea;
            count += other.count;
        }
    }
}

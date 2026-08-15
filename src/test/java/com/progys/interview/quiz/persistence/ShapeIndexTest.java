package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Donut;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Triangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShapeIndexTest {
    private static final StoredShape SMALL_CIRCLE =
            new StoredShape(1L, new Circle(new Point(0, 0), 1));
    private static final StoredShape BIG_CIRCLE =
            new StoredShape(2L, new Circle(new Point(1000, 1000), 500));

    @Test
    void findsShapeFromQueryPointInsideItsBoundingBox() {
        ShapeIndex index = new ShapeIndex();
        index.put(SMALL_CIRCLE);

        assertThat(index.query(new Point(0.5, 0.5))).containsExactly(SMALL_CIRCLE);
    }

    @Test
    void findsShapeSpanningMultipleCellsFromAnyOverlappedCell() {
        ShapeIndex index = new ShapeIndex(1.0);
        StoredShape circle = new StoredShape(3L, new Circle(new Point(0, 0), 2));
        index.put(circle);

        assertThat(index.query(new Point(-1.5, -1.5))).containsExactly(circle);
        assertThat(index.query(new Point(1.5, 1.5))).containsExactly(circle);
    }

    @Test
    void keepsLargeShapesInOverflowListFoundFromAnyPoint() {
        ShapeIndex index = new ShapeIndex(1.0);
        index.put(BIG_CIRCLE);

        assertThat(index.query(new Point(900, 900))).containsExactly(BIG_CIRCLE);
        assertThat(index.query(new Point(1100, 1100))).containsExactly(BIG_CIRCLE);
    }

    @Test
    void combinesCellCandidatesAndLargeShapes() {
        ShapeIndex index = new ShapeIndex(1.0);
        index.put(SMALL_CIRCLE);
        index.put(BIG_CIRCLE);

        assertThat(index.query(new Point(0.5, 0.5))).containsExactly(SMALL_CIRCLE, BIG_CIRCLE);
    }

    @Test
    void returnsEmptyWhenNoShapeNearby() {
        ShapeIndex index = new ShapeIndex();
        index.put(SMALL_CIRCLE);

        assertThat(index.query(new Point(100, 100))).isEmpty();
    }

    @Test
    void queryResultIsDeterministic() {
        ShapeIndex index = new ShapeIndex(1.0);
        index.put(SMALL_CIRCLE);
        index.put(new StoredShape(4L, new Donut(1, 2, new Point(0, 0))));
        index.put(new StoredShape(5L, new Triangle(new Point(-1, -1), new Point(1, -1),
                new Point(-1, 1))));

        List<StoredShape> first = List.copyOf(index.query(new Point(0, 0)));
        List<StoredShape> second = List.copyOf(index.query(new Point(0, 0)));

        assertThat(first).containsExactlyElementsOf(second);
    }

    @Test
    void clearRemovesAllShapes() {
        ShapeIndex index = new ShapeIndex(1.0);
        index.put(SMALL_CIRCLE);
        index.put(BIG_CIRCLE);

        index.clear();

        assertThat(index.query(new Point(0.5, 0.5))).isEmpty();
        assertThat(index.query(new Point(1000, 1000))).isEmpty();
    }
}

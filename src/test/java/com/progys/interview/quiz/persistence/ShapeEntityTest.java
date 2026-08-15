package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Donut;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Triangle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShapeEntityTest {

    @Test
    void mapsCircleRoundTrip() {
        Circle circle = (Circle) new ShapeEntity(new Circle(new Point(1, 2), 3)).toShape();

        assertThat(circle.getCenter().x).isEqualTo(1);
        assertThat(circle.getCenter().y).isEqualTo(2);
        assertThat(circle.getRadius()).isEqualTo(3);
    }

    @Test
    void mapsTriangleRoundTrip() {
        Triangle triangle = (Triangle) new ShapeEntity(
                new Triangle(new Point(0, 0), new Point(1, 1), new Point(2, 0))).toShape();

        assertThat(triangle.getV0().x).isEqualTo(0);
        assertThat(triangle.getV1().x).isEqualTo(1);
        assertThat(triangle.getV2().x).isEqualTo(2);
    }

    @Test
    void mapsDonutRoundTrip() {
        Donut donut = (Donut) new ShapeEntity(new Donut(1, 2, new Point(5, 5))).toShape();

        assertThat(donut.getInnerCircle().getRadius()).isEqualTo(1);
        assertThat(donut.getOuterCircle().getRadius()).isEqualTo(2);
        assertThat(donut.getCenter().x).isEqualTo(5);
        assertThat(donut.getCenter().y).isEqualTo(5);
    }

    @Test
    void rejectsNullShape() {
        assertThatThrownBy(() -> new ShapeEntity(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

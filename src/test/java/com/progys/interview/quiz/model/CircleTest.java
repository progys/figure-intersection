package com.progys.interview.quiz.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CircleTest {
    private final Circle circleWithRadiusOne = new Circle(new Point(1, 1), 1);
    private final Circle circleWithRadiusTwo = new Circle(new Point(1, 1), 2);

    @Test
    public void calculatesAreaWithRadiusOne() {
        assertThat(circleWithRadiusOne.getArea())
                .isCloseTo(Math.PI, Assertions.within(0.01));
    }

    @Test
    public void calculatesAreaWithRadiusTwo() {
        assertThat(circleWithRadiusTwo.getArea())
                .isCloseTo(Math.PI * 4, Assertions.within(0.01));
    }

    @Test
    public void containsPointInsideCircle() {
        assertThat(circleWithRadiusOne.inShape(new Point(1, 1))).isTrue();
        assertThat(circleWithRadiusOne.inShape(new Point(1.5, 1))).isTrue();
    }

    @Test
    public void doesNotContainPointOutsideCircle() {
        assertThat(circleWithRadiusOne.inShape(new Point(3, 1))).isFalse();
    }

    @Test
    public void doesNotContainPointOnBoundary() {
        assertThat(circleWithRadiusOne.inShape(new Point(2, 1))).isFalse();
    }
}
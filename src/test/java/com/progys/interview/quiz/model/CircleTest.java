package com.progys.interview.quiz.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CircleTest {
    private final Circle circleWithRadiusOne = new Circle(new Point(1, 1), 1);
    private final Circle circleWithRadiusTwo = new Circle(new Point(1, 1), 2);

    @Test
    public void calculatesAreaWithRadiusOne() {
        Assertions.assertThat(circleWithRadiusOne.getArea())
                .isCloseTo(Math.PI, Assertions.within(0.01));
    }

    @Test
    public void calculatesAreaWithRadiusTwo() {
        Assertions.assertThat(circleWithRadiusTwo.getArea())
                .isCloseTo(Math.PI * 4, Assertions.within(0.01));
    }
}
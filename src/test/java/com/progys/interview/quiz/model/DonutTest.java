package com.progys.interview.quiz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class DonutTest {
    private final Point center = new Point(1, 1);

    @Test
    public void calculatesDonutArea() {
        assertThat(new Donut(1, 2, center).getArea())
                .isCloseTo(Math.PI * 3, within(0.01));
    }
}
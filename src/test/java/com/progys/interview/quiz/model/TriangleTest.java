package com.progys.interview.quiz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class TriangleTest {
    private final Triangle triangle = new Triangle(new Point(-1, 0), new Point(0, 1), new Point(1, 0));

    @Test
    public void testTriangleArea() {
        assertThat(triangle.getArea())
                .isCloseTo(1, within(0.01));
    }
}
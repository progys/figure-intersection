package com.progys.interview.quiz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

public class TriangleTest {
    private final Triangle triangle = new Triangle(new Point(-1, 0), new Point(0, 1), new Point(1, 0));

    @Test
    public void testTriangleArea() {
        assertThat(triangle.getArea())
                .isCloseTo(1, within(0.01));
    }

    @Test
    public void rejectsTriangleWithIdenticalVertices() {
        assertThatThrownBy(
                () -> new Triangle(new Point(0, 0), new Point(0, 0), new Point(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("collinear");
    }

    @Test
    public void rejectsTriangleWithCollinearVertices() {
        assertThatThrownBy(
                () -> new Triangle(new Point(0, 0), new Point(1, 1), new Point(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("collinear");
    }

    @Test
    public void containsPointInsideTriangle() {
        assertThat(triangle.inShape(new Point(0, 0.5))).isTrue();
    }

    @Test
    public void doesNotContainPointOutsideTriangle() {
        assertThat(triangle.inShape(new Point(2, 0))).isFalse();
        assertThat(triangle.inShape(new Point(0, 1.5))).isFalse();
    }

    @Test
    public void doesNotContainPointOnTriangleBoundary() {
        assertThat(triangle.inShape(new Point(0, 1))).isFalse();
        assertThat(triangle.inShape(new Point(1, 0))).isFalse();
    }
}
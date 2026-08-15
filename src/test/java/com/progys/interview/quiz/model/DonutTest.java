package com.progys.interview.quiz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

public class DonutTest {
    private final Point center = new Point(1, 1);

    @Test
    public void calculatesDonutArea() {
        assertThat(new Donut(1, 2, center).getArea())
                .isCloseTo(Math.PI * 3, within(0.01));
    }

    @Test
    public void rejectsZeroInnerRadius() {
        assertThatThrownBy(() -> new Donut(0, 2, center))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("inner radius should be positive");
    }

    @Test
    public void rejectsZeroOuterRadius() {
        assertThatThrownBy(() -> new Donut(1, 0, center))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("outer radius should be positive");
    }

    @Test
    public void rejectsNegativeRadius() {
        assertThatThrownBy(() -> new Donut(-1, 2, center))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("inner radius should be positive");
    }

    @Test
    public void rejectsEqualInnerAndOuterRadius() {
        assertThatThrownBy(() -> new Donut(2, 2, center))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("equal to outer radius");
    }

    @Test
    public void containsPointInRing() {
        Donut donut = new Donut(1, 2, center);
        assertThat(donut.inShape(new Point(2.5, 1))).isTrue();
    }

    @Test
    public void doesNotContainPointInHole() {
        Donut donut = new Donut(1, 2, center);
        assertThat(donut.inShape(center)).isFalse();
    }

    @Test
    public void doesNotContainPointOutsideOuterCircle() {
        Donut donut = new Donut(1, 2, center);
        assertThat(donut.inShape(new Point(4, 1))).isFalse();
    }
}
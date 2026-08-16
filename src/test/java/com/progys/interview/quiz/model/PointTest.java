package com.progys.interview.quiz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointTest {

    @Test
    void equalPointsAreEqual() {
        assertThat(new Point(1.5, 2.5)).isEqualTo(new Point(1.5, 2.5));
    }

    @Test
    void differentPointsAreNotEqual() {
        assertThat(new Point(1.5, 2.5)).isNotEqualTo(new Point(1.5, 3.0));
    }

    @Test
    void equalPointsHaveSameHashCode() {
        assertThat(new Point(1.5, 2.5).hashCode()).isEqualTo(new Point(1.5, 2.5).hashCode());
    }

    @Test
    void equalsIsReflexive() {
        Point p = new Point(0, 0);
        assertThat(p).isEqualTo(p);
    }

    @Test
    void equalsTreatsNullAsNotEqual() {
        assertThat(new Point(1, 2)).isNotEqualTo(null);
    }
}

package com.progys.interview.quiz.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for circle.
 * 
 * @author progys
 */
public class CircleTest {
    private double delta = 0.01;
    private Point center = new Point(1, 1);

    @Test
    public void testCircleAreaWithRadiusOne() {
        assertEquals(Math.PI, new Circle(center, 1).getArea(), delta);
    }

    @Test
    public void testCircleAreaWithRadiusTwo() {
        assertEquals(Math.PI * 4, new Circle(center, 2).getArea(), delta);
    }
}

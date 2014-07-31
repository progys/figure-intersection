package com.progys.interview.quiz.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author progys
 */
public class DonutTest {
    private Point center = new Point(1, 1);
    private double delta = 0.01;

    @Test
    public void testDonutArea() {
        assertEquals(Math.PI * 3, new Donut(1, 2, center).getArea(), delta);
    }
}

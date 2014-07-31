package com.progys.interview.quiz.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author progys
 */
public class TriangleTest {
    private double delta = 0.01;

    @Test
    public void testTriangleArea() {
        assertEquals(1, new Triangle(new Point(-1, 0), new Point(0, 1), new Point(1, 0)).getArea(),
                delta);
    }
}

package com.progys.interview.quiz.parser;

import com.progys.interview.quiz.exceptions.ParseException;
import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Donut;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.model.Triangle;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShapeParserTest {

    private Shape parse(String input) {
        Scanner scanner = new Scanner(input);
        return new ShapeParser(scanner, new PointParser(scanner)).parse();
    }

    @Test
    void parsesCircle() {
        Circle circle = (Circle) parse("circle 1 2 3");

        assertThat(circle.getCenter().x).isEqualTo(1);
        assertThat(circle.getCenter().y).isEqualTo(2);
        assertThat(circle.getRadius()).isEqualTo(3);
    }

    @Test
    void parsesTriangle() {
        Triangle triangle = (Triangle) parse("triangle 0 0 1 1 2 0");

        assertThat(triangle.getV0().x).isEqualTo(0);
        assertThat(triangle.getV1().x).isEqualTo(1);
        assertThat(triangle.getV2().x).isEqualTo(2);
    }

    @Test
    void parsesDonut() {
        Donut donut = (Donut) parse("donut 0 0 1 2");

        assertThat(donut.getInnerCircle().getRadius()).isEqualTo(1);
        assertThat(donut.getOuterCircle().getRadius()).isEqualTo(2);
    }

    @Test
    void throwsParseExceptionOnUnrecognizedShapeName() {
        assertThatThrownBy(() -> parse("hexagon 1 2 3")).isInstanceOf(ParseException.class);
    }

    @Test
    void throwsParseExceptionOnInvalidRadius() {
        assertThatThrownBy(() -> parse("circle 0 0 0")).isInstanceOf(ParseException.class);
    }

    @Test
    void throwsParseExceptionOnMissingArguments() {
        assertThatThrownBy(() -> parse("triangle 0 0")).isInstanceOf(ParseException.class);
    }
}

package com.progys.interview.quiz.parser;

import com.progys.interview.quiz.exceptions.ParseException;
import com.progys.interview.quiz.model.Point;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointParserTest {

    @Test
    void parsesPoint() {
        Point point = new PointParser(new Scanner("1.5 2.5")).parse();

        assertThat(point.x).isEqualTo(1.5);
        assertThat(point.y).isEqualTo(2.5);
    }

    @Test
    void throwsParseExceptionOnNonNumericInput() {
        PointParser parser = new PointParser(new Scanner("abc 1"));

        assertThatThrownBy(parser::parse).isInstanceOf(ParseException.class);
    }

    @Test
    void throwsParseExceptionOnMissingCoordinate() {
        PointParser parser = new PointParser(new Scanner("1"));

        assertThatThrownBy(parser::parse).isInstanceOf(ParseException.class);
    }
}

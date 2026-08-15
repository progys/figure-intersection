package com.progys.interview.quiz.parser;

import com.progys.interview.quiz.exceptions.ParseException;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralParserFactoryTest {
    private final GeneralParserFactory parserFactory =
            new GeneralParserFactory(new ConcreteParserFactory() {
                @Override
                public Parser<Point> getPointParser(Scanner scanner) {
                    return new PointParser(scanner);
                }

                @Override
                public Parser<Shape> getShapeParser(Scanner scanner, Parser<Point> pointParser) {
                    return new ShapeParser(scanner, pointParser);
                }

                @Override
                public Parser<ParsedAction> getCommandParser(String command) {
                    return new CommandParser(command);
                }
            });

    @Test
    void parsesActionCommand() {
        assertThat(parserFactory.create("list").parse())
                .isEqualTo(new ParsedAction(ActionNames.list));
    }

    @Test
    void parsesEmptyInputAsActionCommand() {
        assertThat(parserFactory.create("").parse())
                .isEqualTo(new ParsedAction(ActionNames.empty));
    }

    @Test
    void parsesPointQuery() {
        ParsedObject parsed = parserFactory.create("1 2").parse();

        assertThat(parsed).isInstanceOf(ParsedPoint.class);
        assertThat(((ParsedPoint) parsed).point().x).isEqualTo(1);
        assertThat(((ParsedPoint) parsed).point().y).isEqualTo(2);
    }

    @Test
    void parsesShape() {
        ParsedObject parsed = parserFactory.create("circle 0 0 1").parse();

        assertThat(parsed).isInstanceOf(ParsedShape.class);
    }

    @Test
    void throwsParseExceptionOnInvalidShape() {
        assertThatThrownBy(() -> parserFactory.create("circle 0 0 0").parse())
                .isInstanceOf(ParseException.class);
    }
}

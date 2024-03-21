package com.progys.interview.quiz.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HelpCommandTest {
    private ByteArrayOutputStream outputContent;
    private HelpCommand helpCommand;

    @BeforeEach
    public void setUp() {
        outputContent = new ByteArrayOutputStream();
        helpCommand = new HelpCommand(new PrintStream(outputContent));
    }

    @Test
    public void printsHelpMessage() {
        helpCommand.process();
        String output = outputContent.toString();
        assertThat(output).contains(
                "Usage: FigureIntersection -f [file]",
                "Supported interactive commands:",
                "Interactive commands examples:"
        );
    }

    @Test
    public void printsCommandDescriptions() {
        helpCommand.process();
        String output = outputContent.toString();
        assertThat(output).contains(
                "triangle <x> <y> <x> <y> <x> <y>  - creates triangle at given points.",
                "donut <x> <y> <radius> <radius>   - creates donut with center at (x, y) with given inner (smaller radius) and outer (larger) radius.",
                "circle <x> <y> <radius>           - creates circle with center at (x, y) and given radius."
        );
    }

    @Test
    public void printsCommandExamples() {
        helpCommand.process();
        String output = outputContent.toString();
        assertThat(output).contains(
                "triangle 4.5 1 -2.5 -33 23 0.3   - creates triangle (4.5,1) (-2.5, -33) (23, 0.3)",
                "donut 1.1 7.8 2 1.8              - creates donut with center at (1.1, 7.8) inner radius 1.8 and outer radius 2",
                "circle 3 5 2                     - creates circle with center at (3, 5) inner radius 1.8 and outer radius 2"
        );
    }
}
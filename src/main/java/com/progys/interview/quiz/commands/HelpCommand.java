package com.progys.interview.quiz.commands;

import com.google.inject.Inject;

import java.io.PrintStream;

public class HelpCommand extends AbstractCommand {

    @Inject
    HelpCommand(PrintStream output) {
        super(output);
    }

    @Override
    public void process() {
        String[] helpMessages = {
                "Usage: FigureIntersection -f [file]",
                "Loads shapes from the provided file.",
                "\nSupported interactive commands: ",
                "  triangle <x> <y> <x> <y> <x> <y>  - creates triangle at given points. ",
                "  donut <x> <y> <radius> <radius>   - creates donut with center at (x, y) with given inner (smaller radius) and outer (larger) radius.",
                "  circle <x> <y> <radius>           - creates circle with center at (x, y) and given radius.",
                "  <x> <y>                           - prints out all existing shapes that include given point (x, y). In addition prints out every shape area and total area.",
                "  list                              - prints all current shapes",
                "  clear                             - delete all shapes",
                "  help                              - prints this help",
                "  exit                              - terminates the program\n",
                "Interactive commands examples: ",
                "  triangle 4.5 1 -2.5 -33 23 0.3   - creates triangle (4.5,1) (-2.5, -33) (23, 0.3)",
                "  donut 1.1 7.8 2 1.8              - creates donut with center at (1.1, 7.8) inner radius 1.8 and outer radius 2",
                "  circle 3 5 2                     - creates circle with center at (3, 5) inner radius 1.8 and outer radius 2",
                "  5.1 6.2                          - prints all shapes which include given point (5.1, 6.2) with their surface area and also total area."
        };

        for (String message : helpMessages) {
            output.println(message);
        }
    }
}
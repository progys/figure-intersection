package com.progys.interview.quiz.commands;

import com.google.inject.Inject;

import java.io.PrintStream;

/**
 * Defines action which prints help menu.
 * 
 * @author progys
 */
public class HelpCommand extends AbstractCommand {

    @Inject
    HelpCommand(PrintStream output) {
        super(output);
    }

    @Override
    public void process() {
        output.println("Usage: FigureIntersection -f [file]");
        output.println("Loads shapes from the provided file.");
        output.println("\nSupported interactive commands: ");
        output.println("  triangle <x> <y> <x> <y> <x> <y>  - creates triangle at given points. ");
        output.println("  donut <x> <y> <radius> <radius>   - creates donut with center at (x, y) with given inner (smaller radius) and outer (larger) radius.");
        output.println("  circle <x> <y> <radius>           - creates circle with center at (x, y) and given radius.");
        output.println("  <x> <y>                           - prints out all existing shapes that include given point (x, y). In addition prints out every shape area and total area.");
        output.println("  list                              - prints all current shapes");
        output.println("  clear                             - delete all shapes");
        output.println("  help                              - prints this help");
        output.println("  exit                              - terminates the program\n");
        output.println("Interactive commands examples: ");
        output.println("  triangle 4.5 1 -2.5 -33 23 0.3   - creates triangle (4.5,1) (-2.5, -33) (23, 0.3)");
        output.println("  donut 1.1 7.8 2 1.8              - creates donut with center at (1.1, 7.8) inner radius 1.8 and outer radius 2");
        output.println("  circle 3 5 2                     - creates circle with center at (3, 5) inner radius 1.8 and outer radius 2");
        output.println("  5.1 6.2                          - prints all shapes which include given point (5.1, 6.2) with their surface area and also total area.");
    }
}

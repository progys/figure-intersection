package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.parser.ParsedAction;
import com.progys.interview.quiz.parser.ParsedObject;
import com.progys.interview.quiz.parser.ParsedPoint;
import com.progys.interview.quiz.parser.ParsedShape;

/**
 * Dispatches a parsed input to the matching command. The switch is exhaustive over the
 * sealed {@link ParsedObject} hierarchy, so the compiler guarantees every input is handled.
 * 
 * @author progys
 */
public class GeneralCommandFactory implements CommandFactory {
    private final ActionCommandFactory actionCommandFactory;

    @Inject
    GeneralCommandFactory(ActionCommandFactory actionCommandFactory) {
        this.actionCommandFactory = actionCommandFactory;
    }

    public Command getCommand(ParsedObject parsed, ShapeOutputMode outputMode) {
        return switch (parsed) {
            case null -> actionCommandFactory.getEmptyCommand();
            case ParsedPoint parsedPoint ->
                actionCommandFactory.getPointCommand(parsedPoint.point());
            case ParsedShape parsedShape ->
                actionCommandFactory.getShapeCommand(outputMode, parsedShape.shape());
            case ParsedAction parsedAction -> switch (parsedAction.name()) {
                case exit -> actionCommandFactory.getExitCommand();
                case help -> actionCommandFactory.getHelpCommand();
                case list -> actionCommandFactory.getListCommand();
                case clear -> actionCommandFactory.getClearCommand();
                case empty -> actionCommandFactory.getEmptyCommand();
            };
        };
    }

}

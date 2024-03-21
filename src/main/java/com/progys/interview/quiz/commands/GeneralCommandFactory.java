package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.parser.NamedObject;

/**
 * @author progys
 */
public class GeneralCommandFactory implements CommandFactory {
    private final ActionCommandFactory actionCommandFactory;

    @Inject
    GeneralCommandFactory(ActionCommandFactory actionCommandFactory) {
        this.actionCommandFactory = actionCommandFactory;
    }

    public Command getCommand(NamedObject parsed, boolean silentCommands) {
        return switch (parsed.getName()) {
            case exit -> actionCommandFactory.getExitCommand();
            case help -> actionCommandFactory.getHelpCommand();
            case list -> actionCommandFactory.getListCommand();
            case clear -> actionCommandFactory.getClearCommand();
            case point -> actionCommandFactory.getPointCommand((Point) parsed);
            case shape -> actionCommandFactory.getShapeCommand(silentCommands, (Shape) parsed);
            default -> actionCommandFactory.getEmptyCommand();
        };
    }

}

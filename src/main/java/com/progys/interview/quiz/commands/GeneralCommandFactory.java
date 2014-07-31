package com.progys.interview.quiz.commands;

import com.google.inject.Inject;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;
import com.progys.interview.quiz.parser.NamedObject;

/**
 * @author progys
 */
public class GeneralCommandFactory implements CommandFactory {
    private ActionCommandFactory actionCommandFactory;

    @Inject
    GeneralCommandFactory(ActionCommandFactory actionCommandFactory) {
        this.actionCommandFactory = actionCommandFactory;
    }

    public Command getCommand(NamedObject parsed, boolean silentCommands) {
        Command command;
        switch (parsed.getName()) {
        case exit:
            command = actionCommandFactory.getExitCommand();
            break;
        case help:
            command = actionCommandFactory.getHelpCommand();
            break;
        case list:
            command = actionCommandFactory.getListCommand();
            break;
        case clear:
            command = actionCommandFactory.getClearCommand();
            break;
        case point:
            command = actionCommandFactory.getPointCommand((Point) parsed);
            break;
        case shape:
            command = actionCommandFactory.getShapeCommand(silentCommands, (Shape) parsed);
            break;
        default:
            command = actionCommandFactory.getEmptyCommand();
        }
        return command;
    }

}

package com.progys.interview.quiz.commands;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;

/**
 * Creates handling action for parsed object.
 * 
 * @author progys
 */
public interface ActionCommandFactory {
    @Named("empty")
    Command getEmptyCommand();

    @Named("exit")
    Command getExitCommand();

    @Named("help")
    Command getHelpCommand();

    @Named("list")
    Command getListCommand();

    @Named("clear")
    Command getClearCommand();

    @Named("point")
    Command getPointCommand(@Assisted Point point);

    @Named("shape")
    Command getShapeCommand(@Assisted Boolean silent, @Assisted Shape shape);

}

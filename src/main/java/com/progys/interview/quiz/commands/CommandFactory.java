package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.parser.NamedObject;

/**
 * @author progys
 */
public interface CommandFactory {
    public Command getCommand(NamedObject parsed, boolean silentCommands);
}

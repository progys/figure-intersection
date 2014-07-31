package com.progys.interview.quiz.parser;

import com.google.common.base.Strings;
import com.google.inject.assistedinject.Assisted;
import com.progys.interview.quiz.model.NamedObjectCommand;

import javax.inject.Inject;

/**
 * Defines commands parser.
 * 
 * @author progys
 */
public class CommandParser implements Parser<NamedObject> {
    private final String command;

    @Inject
    public CommandParser(@Assisted String command) {
        this.command = command;
    }

    @Override
    public NamedObject parse() {
        if (Strings.isNullOrEmpty(command)) {
            return new NamedObjectCommand(CommandNames.empty);
        }
        return new NamedObjectCommand(CommandNames.valueOf(command));
    }
}

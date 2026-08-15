package com.progys.interview.quiz.parser;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Defines commands parser.
 * 
 * @author progys
 */
public class CommandParser implements Parser<ParsedAction> {
    private final String command;

    @Inject
    public CommandParser(@Assisted String command) {
        this.command = command;
    }

    @Override
    public ParsedAction parse() {
        if (Strings.isNullOrEmpty(command)) {
            return new ParsedAction(ActionNames.empty);
        }
        return new ParsedAction(ActionNames.valueOf(command));
    }
}

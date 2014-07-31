package com.progys.interview.quiz.model;

import com.progys.interview.quiz.parser.CommandNames;
import com.progys.interview.quiz.parser.NamedObject;

/**
 * Defines parsed command.
 * 
 * @author progys
 */
public class NamedObjectCommand implements NamedObject {
    private final CommandNames name;

    public NamedObjectCommand(CommandNames commandNames) {
        this.name = commandNames;
    }

    @Override
    public CommandNames getName() {
        return name;
    }
}

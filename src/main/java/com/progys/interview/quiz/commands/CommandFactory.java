package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.parser.ParsedObject;

/**
 * @author progys
 */
public interface CommandFactory {
    Command getCommand(ParsedObject parsed, ShapeOutputMode outputMode);
}

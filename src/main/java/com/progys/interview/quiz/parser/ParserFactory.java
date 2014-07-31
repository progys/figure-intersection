package com.progys.interview.quiz.parser;

/**
 * @author progys
 */
public interface ParserFactory {
    public Parser<NamedObject> create(String command);
}

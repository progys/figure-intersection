package com.progys.interview.quiz.parser;

/**
 * @author progys
 */
public interface ParserFactory {
    Parser<ParsedObject> create(String command);
}

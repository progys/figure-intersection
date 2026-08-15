package com.progys.interview.quiz.parser;

/**
 * A parsed action command (list, exit, clear, help, empty).
 * 
 * @author progys
 */
public record ParsedAction(ActionNames name) implements ParsedObject {
}

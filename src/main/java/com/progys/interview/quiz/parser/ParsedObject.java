package com.progys.interview.quiz.parser;

/**
 * A parsed console/file line, discriminated by type instead of by name.
 * 
 * @author progys
 */
public sealed interface ParsedObject permits ParsedAction, ParsedPoint, ParsedShape {
}

package com.progys.interview.quiz.parser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandParserTest {

    @Test
    void parsesKnownCommands() {
        assertThat(new CommandParser("list").parse())
                .isEqualTo(new ParsedAction(ActionNames.list));
        assertThat(new CommandParser("exit").parse())
                .isEqualTo(new ParsedAction(ActionNames.exit));
        assertThat(new CommandParser("help").parse())
                .isEqualTo(new ParsedAction(ActionNames.help));
        assertThat(new CommandParser("clear").parse())
                .isEqualTo(new ParsedAction(ActionNames.clear));
    }

    @Test
    void parsesEmptyInputAsEmptyCommand() {
        assertThat(new CommandParser("").parse())
                .isEqualTo(new ParsedAction(ActionNames.empty));
    }

    @Test
    void throwsOnUnknownCommand() {
        assertThatThrownBy(() -> new CommandParser("bogus").parse())
                .isInstanceOf(IllegalArgumentException.class);
    }
}

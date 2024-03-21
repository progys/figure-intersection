package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.persistence.Store;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

class ClearCommandTest {

    @Test
    void clearsStore() {
        Store mockStore = Mockito.mock(Store.class);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);

        ClearCommand clearCommand = new ClearCommand(mockStore, mockPrintStream);

        clearCommand.process();

        verify(mockStore).clear();
    }
}
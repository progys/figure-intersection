package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.persistence.Store;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

class ExitCommandTest {
    @Test
    void closesStoreAndExitsJVM() {
        Store mockStore = mock(Store.class);
        PrintStream mockPrintStream = mock(PrintStream.class);
        ExitCommand exitCommand = new ExitCommand(mockStore, mockPrintStream);

        Runtime runtime = mock(Runtime.class);
        doNothing().when(runtime).exit(0);

        try (MockedStatic<Runtime> mockedRuntime = mockStatic(Runtime.class)) {
            mockedRuntime.when(Runtime::getRuntime).thenReturn(runtime);
            exitCommand.process();
        } catch (Exception ignored) {
        }

        verify(mockStore).close();
        verify(runtime).exit(0);
    }
}
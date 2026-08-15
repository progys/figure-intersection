package com.progys.interview.quiz.commands;

import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.persistence.StoredShape;
import com.progys.interview.quiz.persistence.Store;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PointCommandTest {

    @Test
    void printsContainingShapesInEncounterOrder() {
        Store mockStore = Mockito.mock(Store.class);
        StoredShape insideSmall = new StoredShape(1L, new Circle(new Point(0, 0), 1));
        StoredShape outside = new StoredShape(2L, new Circle(new Point(100, 100), 1));
        StoredShape insideBig = new StoredShape(3L, new Circle(new Point(0, 0), 2));
        when(mockStore.getAll()).thenReturn(List.of(insideSmall, outside, insideBig));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PointCommand pointCommand =
                new PointCommand(new Point(0, 0), mockStore, new PrintStream(outputStream));

        pointCommand.process();

        String output = outputStream.toString();
        assertThat(output).containsSubsequence("=> Shape 1:", "=> Shape 3:");
        assertThat(output).doesNotContain("=> Shape 2:");
        assertThat(output).contains(
                "Found 2 shapes containing point (0.0, 0.0). Surface area combined:  15.7080");
    }

    @Test
    void printsNoShapesFoundWhenNothingContainsPoint() {
        Store mockStore = Mockito.mock(Store.class);
        StoredShape outside = new StoredShape(1L, new Circle(new Point(100, 100), 1));
        when(mockStore.getAll()).thenReturn(List.of(outside));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PointCommand pointCommand =
                new PointCommand(new Point(0, 0), mockStore, new PrintStream(outputStream));

        pointCommand.process();

        assertThat(outputStream.toString()).contains("No shapes found. Total surface area: 0");
    }
}

package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.Circle;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Triangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectStoreTest {
    private Path dbPath;
    private ObjectStore store;

    @BeforeAll
    static void enhancePersistenceClasses() {
        com.objectdb.Enhancer.enhance("com.progys.interview.quiz.persistence.*");
    }

    @BeforeEach
    void setUp() throws IOException {
        dbPath = Files.createTempDirectory("odbtest").resolve("test.odb");
        store = new ObjectStore(createEntityManagerFactory());
    }

    @AfterEach
    void tearDown() throws IOException {
        try {
            store.close();
        } catch (RuntimeException ignored) {
        }
        if (dbPath.getParent() != null && Files.exists(dbPath.getParent())) {
            try (var paths = Files.walk(dbPath.getParent())) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException ignored) {
                    }
                });
            }
        }
    }

    private EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("objectdb:" + dbPath);
    }

    @Test
    void putAssignsIdAndStoresShape() {
        StoredShape stored = store.put(new Circle(new Point(1, 2), 3));

        assertThat(stored.id()).isNotNull();
        Collection<StoredShape> all = store.getAll();
        assertThat(all).hasSize(1);
        assertThat(all.iterator().next().shape()).isInstanceOf(Circle.class);
    }

    @Test
    void clearEmptiesStore() {
        store.put(new Circle(new Point(0, 0), 1));

        store.clear();

        assertThat(store.getAll()).isEmpty();
    }

    @Test
    void loadsPersistedShapesFromDatabaseOnStartup() {
        store.put(new Triangle(new Point(0, 0), new Point(1, 0), new Point(0, 1)));
        store.close();

        ObjectStore reopened = new ObjectStore(createEntityManagerFactory());
        try {
            assertThat(reopened.getAll()).hasSize(1);
            assertThat(reopened.getAll().iterator().next().shape()).isInstanceOf(Triangle.class);
        } finally {
            reopened.close();
        }
    }
}

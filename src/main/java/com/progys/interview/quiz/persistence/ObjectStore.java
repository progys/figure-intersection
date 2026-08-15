package com.progys.interview.quiz.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.progys.interview.quiz.model.Point;
import com.progys.interview.quiz.model.Shape;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object storage layer. The database is the source of truth across restarts, but a copy of all
 * shapes is kept in memory so point queries never hit the database (the quiz requires queries to
 * scale to tens of millions of shapes held in program memory).
 *
 * @author progys
 */
@Singleton
public class ObjectStore implements Store {
    private static final Logger LOGGER = Logger.getLogger(ObjectStore.class.getName());

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager manager;
    private final List<StoredShape> shapes;
    private final ShapeIndex index;

    @Inject
    ObjectStore(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.manager = entityManagerFactory.createEntityManager();
        this.shapes = new ArrayList<>(loadShapesFromDatabase());
        this.index = new ShapeIndex();
        shapes.forEach(index::put);
    }

    @Override
    public StoredShape put(Shape shape) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            ShapeEntity entity = new ShapeEntity(shape);
            manager.persist(entity);
            manager.flush();
            transaction.commit();
            StoredShape stored = new StoredShape(entity.getId(), shape);
            shapes.add(stored);
            index.put(stored);
            return stored;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error while storing shape into DB", e);
            throw new StoreException("Error while storing shape into DB", e);
        }
    }

    @Override
    public void clear() {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            manager.createQuery("delete from ShapeEntity").executeUpdate();
            transaction.commit();
            shapes.clear();
            index.clear();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error while clearing shapes from DB", e);
            throw new StoreException("Error while clearing shapes from DB", e);
        }
    }

    @Override
    public Collection<StoredShape> getAll() {
        return Collections.unmodifiableList(shapes);
    }

    @Override
    public Collection<StoredShape> queryContaining(Point point) {
        return index.query(point);
    }

    private List<StoredShape> loadShapesFromDatabase() {
        TypedQuery<ShapeEntity> query = manager.createQuery(
                "SELECT e FROM " + ShapeEntity.class.getName() + " e", ShapeEntity.class);
        return query.getResultList().stream()
                .map(entity -> new StoredShape(entity.getId(), entity.toShape()))
                .toList();
    }

    @Override
    public void close() {
        try {
            manager.close();
        } finally {
            entityManagerFactory.close();
        }
    }
}

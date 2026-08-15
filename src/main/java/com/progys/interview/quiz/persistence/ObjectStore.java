package com.progys.interview.quiz.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.progys.interview.quiz.model.Shape;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines an object storage layer.
 *
 * @author progys
 */
@Singleton
public class ObjectStore implements Store {
    private static final Logger LOGGER = Logger.getLogger(ObjectStore.class.getName());

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager manager;

    @Inject
    ObjectStore(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.manager = entityManagerFactory.createEntityManager();
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
            return new StoredShape(entity.getId(), shape);
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
        TypedQuery<ShapeEntity> query = manager.createQuery(
                "SELECT e FROM " + ShapeEntity.class.getName() + " e", ShapeEntity.class);
        List<ShapeEntity> entities = query.getResultList();
        return entities.stream()
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

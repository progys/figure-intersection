package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.AbstractEntity;
import com.progys.interview.quiz.model.Shape;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;

/**
 * Defines an object storage layer.
 * 
 * @author progys
 */
@Singleton
public class ObjectStore implements Store {
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager manager;

    @Inject
    ObjectStore(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.manager = entityManagerFactory.createEntityManager();
    }

    public Shape put(Shape shape) {
        AbstractEntity entity = new AbstractEntity(shape);
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(entity);
            manager.flush();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error while storing shape into DB" + e.getMessage());
            transaction.rollback();
        }
        return entity;
    }

    public void clear() {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.createQuery("delete from Object").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error while deleting object from DB" + e.getMessage());
            transaction.rollback();
        }
    }

    public Collection<Shape> getAll() {
        TypedQuery<AbstractEntity> selectShapesQuery = manager.createQuery("SELECT e FROM "
                + AbstractEntity.class.getCanonicalName() + " e", AbstractEntity.class);
        Collection<? extends Shape> shapes = selectShapesQuery.getResultList();
        return Collections.unmodifiableCollection(shapes);
    }

    public void close() {
        try {
            manager.close();
        } finally {
            entityManagerFactory.close();
        }
    }
}

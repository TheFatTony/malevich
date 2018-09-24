package io.malevich.server.dao;

import io.malevich.server.entity.Entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JpaDao<T extends Entity, I> implements Dao<T, I> {

    @PersistenceContext
    EntityManager entityManager;

    protected Class<T> entityClass;

    public JpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public T find(I id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void delete(I entityId) {
        T entity = find(entityId);
        delete(entity);
    }
}

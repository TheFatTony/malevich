package io.malevich.server.dao;

import io.malevich.server.entity.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaDao<T extends Entity, I> implements Dao<T, I> {

    protected Class<T> entityClass;
    @PersistenceContext
    EntityManager entityManager;

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
        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = builder.createQuery(this.entityClass);

        Root<T> root = criteriaQuery.from(this.entityClass);

        criteriaQuery.select(root);
        TypedQuery<T> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        if (pageable.isUnpaged())
            return null;

        return null;
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

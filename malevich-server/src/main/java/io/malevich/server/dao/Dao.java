package io.malevich.server.dao;


import io.malevich.server.entity.Entity;

import java.util.List;

public interface Dao<T extends Entity, I> {
    List<T> findAll();

    T find(I id);

    void create(T entity);

    T update(T entity);

    void delete(I id);

    void delete(T entity);
}

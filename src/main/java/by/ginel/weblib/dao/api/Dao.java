package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.AbstractEntity;

import java.util.List;

public interface Dao<T extends AbstractEntity> {

    T save(T entity);

    void delete(Long id);

    void update(T newEntity);

    T getById(Long id);

    List<T> getAll();
}
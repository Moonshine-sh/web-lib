package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.Dao;
import by.ginel.weblib.entity.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
public abstract class AbstractDao<T extends AbstractEntity> implements Dao<T> {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    @Transactional
    @Override
    public T save(T entity) {
        log.info("Executing method save()");
        log.debug("Executing method save() for the entity: {}", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Executing method delete()");
        log.debug("Executing method delete() for the entity with id: {}", id);
        entityManager.remove(entityManager.find(getEntityClass(), id));
    }

    @Transactional
    @Override
    public void update(T newEntity) {
        log.info("Executing method update()");
        log.debug("Executing method update() for the entity: {}", newEntity);
        entityManager.merge(newEntity);
    }

    @Override
    public T getById(Long id) {
        log.info("Executing method getById()");
        log.debug("Executing method getById() for the entity with id: {}", id);
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public List<T> getAll() {
        log.info("Executing method getAll()");
        log.debug("Executing method getAll()");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        return entityManager.createQuery(query).getResultList();
    }
}

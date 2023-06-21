package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.entity.PersonCred;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class PersonCredDaoImpl extends AbstractDao<PersonCred> implements PersonCredDao {
    @Override
    protected Class<PersonCred> getEntityClass() {
        return PersonCred.class;
    }

    @Override
    public PersonCred findByLogin(String login) throws NoResultException {
        log.info("Executing method findByLogin()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonCred> cq = cb.createQuery(PersonCred.class);
        Root<PersonCred> root = cq.from(PersonCred.class);
        cq.select(root).where(cb.equal(root.get("login"), login));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.entity.PersonRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class PersonRoleDaoImpl extends AbstractDao<PersonRole> implements PersonRoleDao {
    @Override
    protected Class<PersonRole> getEntityClass() {
        return PersonRole.class;
    }

    @Override
    public PersonRole findByName(String name) {
        log.info("Executing method findByName()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonRole> cq = cb.createQuery(PersonRole.class);
        Root<PersonRole> root = cq.from(PersonRole.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

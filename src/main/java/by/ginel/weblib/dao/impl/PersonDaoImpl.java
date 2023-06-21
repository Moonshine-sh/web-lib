package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class PersonDaoImpl extends AbstractDao<Person> implements PersonDao {

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public List<Person> findAllByName(String name) {
        log.info("Executing method findAllByName()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root).where(
                cb.or(
                        cb.equal(root.get("first_name"), name),
                        cb.equal(root.get("last_name"), name)
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }
}

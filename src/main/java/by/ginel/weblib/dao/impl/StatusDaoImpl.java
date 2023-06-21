package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.StatusDao;
import by.ginel.weblib.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class StatusDaoImpl extends AbstractDao<Status> implements StatusDao {
    @Override
    protected Class<Status> getEntityClass() {
        return Status.class;
    }

    @Override
    public Status findByName(String name) {
        log.info("Executing method findByName()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Status> cq = cb.createQuery(Status.class);
        Root<Status> root = cq.from(Status.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.VerificationTokenDao;
import by.ginel.weblib.entity.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class VerificationTokenDaoImpl extends AbstractDao<VerificationToken> implements VerificationTokenDao {

    @Override
    protected Class<VerificationToken> getEntityClass() {
        return VerificationToken.class;
    }

    @Override
    public VerificationToken findByToken(String token) throws NoResultException {
        log.info("Executing method findByToken()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VerificationToken> cq = cb.createQuery(VerificationToken.class);
        Root<VerificationToken> root = cq.from(VerificationToken.class);
        cq.select(root).where(cb.equal(root.get("token"), token));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

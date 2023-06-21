package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.GenreDao;
import by.ginel.weblib.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@Repository
public class GenreDaoImpl extends AbstractDao<Genre> implements GenreDao {
    @Override
    protected Class<Genre> getEntityClass() {
        return Genre.class;
    }

    @Override
    public Genre findByName(String name) {
        log.info("Executing method findByName()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
        Root<Genre> root = cq.from(Genre.class);
        cq.select(root).where(cb.equal(root.get("name"), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}

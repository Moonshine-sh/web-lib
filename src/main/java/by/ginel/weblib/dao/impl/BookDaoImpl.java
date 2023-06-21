package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class BookDaoImpl extends AbstractDao<Book> implements BookDao {

    private String getCriteriaLikeValue(String value) {
        return "%" + value.toLowerCase() + "%";
    }

    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    public List<Book> findAllByName(String name) {
        log.info("Executing method findAllByName()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root).where(
                cb.or(
                        cb.like(root.get("name"), getCriteriaLikeValue(name)),
                        cb.like(root.get("author"), getCriteriaLikeValue(name))
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Book> findAllByAuthor(String author) {
        log.info("Executing method findAllByAuthor()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root).where(cb.equal(root.get("author"), author));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Book> findAllByGenre(Genre genre) {
        log.info("Executing method findAllByGenre()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);
        cq.select(root).where(cb.equal(root.get("genre"), genre));
        return entityManager.createQuery(cq).getResultList();
    }
}

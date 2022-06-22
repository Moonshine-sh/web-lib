package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.Genre;

import java.util.List;

public interface BookDao extends Dao<Book> {

    List<Book> findAllByName(String name);

    List<Book> findAllByAuthor(String author);

    List<Book> findAllByGenre(Genre genre);
}

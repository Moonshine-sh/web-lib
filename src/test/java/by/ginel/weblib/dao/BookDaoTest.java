package by.ginel.weblib.dao;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.Genre;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookDaoTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void saveTest(){
        Book book = new Book();
        book.setName("book1");
        book.setGenre(Genre.MANUAL);

        Book newBook = bookDao.save(book);

        Assertions.assertNotEquals(null, newBook.getId());
    }

    @Test
    public void deleteTest(){
        Book book = new Book();
        book.setName("book1");
        book.setGenre(Genre.MANUAL);

        Book newBook = bookDao.save(book);

        Assertions.assertEquals(1, bookDao.getAll().size());

        bookDao.delete(newBook.getId());

        Assertions.assertEquals(0, bookDao.getAll().size());
    }

    @Test
    public void updateTest(){

        Book book = new Book();
        book.setName("book1");
        book.setGenre(Genre.MANUAL);

        Book newBook = bookDao.save(book);

        newBook.setGenre(Genre.ACTION);

        bookDao.update(newBook);

        Assertions.assertEquals(Genre.ACTION, bookDao.getById(newBook.getId()).getGenre());
    }

    @Test
    public void getByIdTest(){

        Book book = new Book();
        book.setName("book1");
        book.setGenre(Genre.MANUAL);

        Book newBook = bookDao.save(book);

        Assertions.assertEquals(newBook.getName(), bookDao.getById(newBook.getId()).getName());
    }

    @Test
    public void getAllTest(){

        Book book1 = new Book();
        book1.setName("book1");
        book1.setGenre(Genre.MANUAL);

        Book book2 = new Book();
        book2.setName("book2");
        book2.setGenre(Genre.MANUAL);

        Book book3 = new Book();
        book3.setName("book3");
        book3.setGenre(Genre.MANUAL);

        bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);

        Assertions.assertEquals(3, bookDao.getAll().size());
    }


}

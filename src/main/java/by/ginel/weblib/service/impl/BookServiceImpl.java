package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.dao.entity.Book;
import by.ginel.weblib.dao.entity.Genre;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.service.dto.BookCreateDto;
import by.ginel.weblib.service.dto.BookGetDto;
import by.ginel.weblib.service.dto.BookUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Transactional
    @Override
    public void save(BookCreateDto bookCreateDto) {
        bookDao.save(
                Book.builder()
                        .name(bookCreateDto.getName())
                        .author(bookCreateDto.getAuthor())
                        .description(bookCreateDto.getDescription())
                        .price(bookCreateDto.getPrice())
                        .cover(bookCreateDto.getPicPath())
                        .genre(bookCreateDto.getGenre())
                        .build()
        );
    }

    @Transactional
    @Override
    public void delete(Long id) { bookDao.delete(id);
    }

    @Transactional
    @Override
    public void update(BookUpdateDto bookUpdateDto) {
        Book book = new Book();
        book.setId(bookUpdateDto.getId());
        book.setName(bookUpdateDto.getName());
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setDescription(bookUpdateDto.getDescription());
        book.setPrice(bookUpdateDto.getPrice());
        book.setCover(bookUpdateDto.getPicPath());
        book.setGenre(bookUpdateDto.getGenre());

        bookDao.update(book);
    }

    @Override
    public BookGetDto getById(Long id) {
        Book book = bookDao.getById(id);
        return  BookGetDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .picPath(book.getCover())
                .genre(book.getGenre().toString())
                .build();
    }

    @Override
    public List<BookGetDto> getAll() {
        List<Book> books = bookDao.getAll();
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getCover())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByName(String name) {
        List<Book> books = bookDao.findAllByName(name);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getCover())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByAuthor(String author) {
        List<Book> books = bookDao.findAllByAuthor(author);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getCover())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByGenre(Genre genre) {
        List<Book> books = bookDao.findAllByGenre(genre);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getCover())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

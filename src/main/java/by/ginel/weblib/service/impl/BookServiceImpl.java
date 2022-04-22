package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.Genre;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    BookDao bookDao;

    @Transactional
    @Override
    public BookGetDto save(BookCreateDto bookCreateDto) {
        Book book = bookDao.save(
                Book.builder()
                        .name(bookCreateDto.getName())
                        .author(bookCreateDto.getAuthor())
                        .description(bookCreateDto.getDescription())
                        .price(bookCreateDto.getPrice())
                        .picPath(bookCreateDto.getPicPath())
                        .genre(bookCreateDto.getGenre())
                        .build()
        );
        return BookGetDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .picPath(book.getPicPath())
                .genre(book.getGenre().toString())
                .build();
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
        book.setPicPath(bookUpdateDto.getPicPath());
        book.setGenre(bookUpdateDto.getGenre());

        bookDao.update(book);
    }

    @Override
    public BookGetDto getById(Long id) throws NullPointerException{
        Book book = bookDao.getById(id);
        return  BookGetDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .price(book.getPrice())
                .picPath(book.getPicPath())
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
                        .picPath(book.getPicPath())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByName(String name) {
        log.info("Executing method findAllByName()");
        List<Book> books = bookDao.findAllByName(name);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getPicPath())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByAuthor(String author) {
        log.info("Executing method findAllByAuthor()");
        List<Book> books = bookDao.findAllByAuthor(author);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getPicPath())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<BookGetDto> findAllByGenre(Genre genre) {
        log.info("Executing method findAllByGenre()");
        List<Book> books = bookDao.findAllByGenre(genre);
        return books
                .stream()
                .map(book -> BookGetDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .description(book.getDescription())
                        .price(book.getPrice())
                        .picPath(book.getPicPath())
                        .genre(book.getGenre().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public String filePathCreate(MultipartFile file){
        log.info("Executing method filePathCreate()");
        if(file!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFilename = UUID.randomUUID().toString();
            return uuidFilename + "." + file.getOriginalFilename();
        }
        return null;
    }
}

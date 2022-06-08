package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.Genre;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Value("${upload.path}")
    private String uploadPath;

    private final BookDao bookDao;

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
    public void delete(Long id) {
        bookDao.delete(id);
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
    public BookGetDto getById(Long id) throws NullPointerException {
        Book book = bookDao.getById(id);
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
    public String filePathCreate(MultipartFile file) {
        log.info("Executing method filePathCreate()");
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFilename = UUID.randomUUID().toString();
            return uuidFilename + "." + file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public void addBookToCart(Long id, Long quantity, HttpServletRequest request) {
        List<CartBook> cart = (List<CartBook>) request.getSession().getAttribute("cart");
        if (cart == null)
            cart = new ArrayList<>();
        cart.add(new CartBook(id, quantity));
        request.getSession().setAttribute("cart", cart);
    }

    @Override
    public void saveBookWithCover(BookCreateDto book, MultipartFile cover) throws IOException {
        String resultFilename = filePathCreate(cover);
        cover.transferTo(new File(uploadPath + "/" + resultFilename));
        book.setPicPath(resultFilename);
        save(book);
    }

    @Override
    public void updateBookWithCover(BookUpdateDto book, MultipartFile cover) throws IOException {
        String resultFilename = filePathCreate(cover);
        cover.transferTo(new File(uploadPath + "/" + resultFilename));
        book.setPicPath(resultFilename);
        update(book);
    }

    @Override
    public List<BookGetDto> getBooksFromCart(List<CartBook> cart) {
        return cart.stream()
                .map(item -> getById(item.getBookId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeBookFromCart(Long id, List<CartBook> cart) {
        Optional<CartBook> book = cart.stream()
                .filter(item -> item.getBookId().equals(id))
                .findAny();
        book.ifPresent(cart::remove);
        return book.isPresent();
    }
}

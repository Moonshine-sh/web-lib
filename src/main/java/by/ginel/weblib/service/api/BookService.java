package by.ginel.weblib.service.api;

import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.Genre;
import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface BookService extends Service<BookCreateDto, BookUpdateDto, BookGetDto>{

    List<BookGetDto> findAllByName(String name);
    List<BookGetDto> findAllByAuthor(String author);
    List<BookGetDto> findAllByGenre(Genre genre);
    String filePathCreate(MultipartFile file);
    void addBookToCart(Long id, Long quantity, HttpServletRequest request);
    void saveBookWithCover(BookCreateDto book, MultipartFile cover) throws IOException;
    void updateBookWithCover(BookUpdateDto book, MultipartFile cover) throws IOException;
    List<BookGetDto> getBooksFromCart(List<CartBook> cart);
    boolean removeBookFromCart(Long id, List<CartBook> cart);
}

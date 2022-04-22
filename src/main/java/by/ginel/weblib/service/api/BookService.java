package by.ginel.weblib.service.api;

import by.ginel.weblib.entity.Genre;
import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService extends Service<BookCreateDto, BookUpdateDto, BookGetDto>{

    List<BookGetDto> findAllByName(String name);
    List<BookGetDto> findAllByAuthor(String author);
    List<BookGetDto> findAllByGenre(Genre genre);
    String filePathCreate(MultipartFile file);
}

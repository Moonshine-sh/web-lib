package by.ginel.weblib.service.api;

import by.ginel.weblib.dao.entity.Genre;
import by.ginel.weblib.service.dto.BookCreateDto;
import by.ginel.weblib.service.dto.BookGetDto;
import by.ginel.weblib.service.dto.BookUpdateDto;

import java.util.List;

public interface BookService extends Service<BookCreateDto, BookUpdateDto, BookGetDto>{

    List<BookGetDto> findAllByName(String name);
    List<BookGetDto> findAllByAuthor(String author);
    List<BookGetDto> findAllByGenre(Genre genre);
}

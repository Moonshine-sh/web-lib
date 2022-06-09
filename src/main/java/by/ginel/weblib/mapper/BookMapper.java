package by.ginel.weblib.mapper;

import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import by.ginel.weblib.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookGetDto mapToBookGetDto(Book book);

    Book mapToBook(BookCreateDto bookCreateDto);

    Book mapToBook(BookUpdateDto bookUpdateDto);
}

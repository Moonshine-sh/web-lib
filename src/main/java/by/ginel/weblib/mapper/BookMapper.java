package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.GenreDao;
import by.ginel.weblib.dto.BookCreateDto;
import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.BookUpdateDto;
import by.ginel.weblib.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public abstract class BookMapper {

    @Autowired
    protected GenreDao genreDao;

    @Mapping(target = "genre", expression = "java(book.getGenre().stream().map(genre -> String.valueOf(genre.getName())).collect(Collectors.toList()))")
    public abstract BookGetDto mapToBookGetDto(Book book);

    @Mapping(target = "genre", expression = "java(bookCreateDto.getGenre().stream().map(genreDao::findByName).collect(Collectors.toList()))")
    public abstract Book mapToBook(BookCreateDto bookCreateDto);

    @Mapping(target = "genre", expression = "java(bookUpdateDto.getGenre().stream().map(genreDao::findByName).collect(Collectors.toList()))")
    public abstract Book mapToBook(BookUpdateDto bookUpdateDto);
}

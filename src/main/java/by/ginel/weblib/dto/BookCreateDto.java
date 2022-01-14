package by.ginel.weblib.dto;

import by.ginel.weblib.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BookCreateDto extends AbstractCreateDto{

    private String name;
    private String author;
    private String description;
    private Double price;
    private String picPath;
    private String genre;

    public Genre getGenre() {
        return Genre.valueOf(genre);
    }
}

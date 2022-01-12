package by.ginel.weblib.service.dto;

import by.ginel.weblib.dao.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BookUpdateDto extends AbstractUpdateDto{

    private Long id;
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

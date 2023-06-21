package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BookGetDto extends AbstractGetDto {

    private Long id;
    private String name;
    private String author;
    private String description;
    private Double price;
    private String picPath;
    private List<String> genre;
}

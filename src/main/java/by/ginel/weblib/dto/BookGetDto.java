package by.ginel.weblib.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BookGetDto extends AbstractGetDto{

    private Long id;
    private String name;
    private String author;
    private String description;
    private Double price;
    private String picPath;
    private String genre;
}

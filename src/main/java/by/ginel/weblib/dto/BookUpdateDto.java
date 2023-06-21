package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BookUpdateDto extends AbstractUpdateDto {

    private Long id;
    @NotBlank(message = "Name cant be NULL")
    private String name;
    @NotBlank(message = "Author cant be NULL")
    private String author;
    @NotBlank(message = "Description cant be NULL")
    private String description;
    @Positive(message = "Price must be positive number")
    private Double price;
    private String picPath;
    @NotBlank(message = "Genre cant be NULL")
    private List<String> genre;
}

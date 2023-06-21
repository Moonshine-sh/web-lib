package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderGetDto extends AbstractGetDto {

    private Long id;
    private String date;
    private Long price;
    private Long personId;
    private List<String> status;
}

package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderCreateDto extends AbstractCreateDto {

    private String date;
    private Long price;
    private Long personId;
    private List<String> status;
}

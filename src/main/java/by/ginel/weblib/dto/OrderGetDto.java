package by.ginel.weblib.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderGetDto extends AbstractGetDto{

    private Long id;
    private String date;
    private Long personId;
    private String status;
}

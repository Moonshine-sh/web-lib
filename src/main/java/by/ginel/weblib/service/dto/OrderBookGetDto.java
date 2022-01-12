package by.ginel.weblib.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderBookGetDto extends AbstractGetDto{

    private Long id;
    private Long quantity;
    private Long bookId;
    private Long orderId;
}

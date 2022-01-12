package by.ginel.weblib.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderBookCreateDto extends AbstractCreateDto{

    private Long quantity;
    private Long bookId;
    private Long orderId;
}

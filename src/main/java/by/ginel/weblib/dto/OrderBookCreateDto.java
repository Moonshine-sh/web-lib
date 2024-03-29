package by.ginel.weblib.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderBookCreateDto extends AbstractCreateDto {

    @NotEmpty(message = "Quantity name cant be NULL")
    private Long quantity;
    private Long bookId;
    private Long orderId;
}

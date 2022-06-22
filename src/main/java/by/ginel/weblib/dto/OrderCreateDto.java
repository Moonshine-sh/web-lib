package by.ginel.weblib.dto;

import by.ginel.weblib.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderCreateDto extends AbstractCreateDto {

    private String date;
    private Long price;
    private Long personId;
    private String status;

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }
}

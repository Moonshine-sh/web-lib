package by.ginel.weblib.service.dto;

import by.ginel.weblib.dao.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OrderUpdateDto extends AbstractUpdateDto{

    private Long id;
    private String date;
    private Long personId;
    private String status;

    public Date getDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("MSC"));
            return dateFormat.parse(date);
        } catch (ParseException e){
            return null;
        }
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }
}

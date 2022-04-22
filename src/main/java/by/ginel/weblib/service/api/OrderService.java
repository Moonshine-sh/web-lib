package by.ginel.weblib.service.api;

import by.ginel.weblib.entity.OrderStatus;
import by.ginel.weblib.dto.OrderCreateDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;

import java.util.List;

public interface OrderService extends Service<OrderCreateDto, OrderUpdateDto, OrderGetDto>{

    List<OrderGetDto> getAllByPersonId(Long id);
    void updateStatus(OrderUpdateDto newOrder);
}

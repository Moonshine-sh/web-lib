package by.ginel.weblib.service.api;

import by.ginel.weblib.dao.entity.OrderStatus;
import by.ginel.weblib.service.dto.OrderCreateDto;
import by.ginel.weblib.service.dto.OrderGetDto;
import by.ginel.weblib.service.dto.OrderUpdateDto;

import java.util.List;

public interface OrderService extends Service<OrderCreateDto, OrderUpdateDto, OrderGetDto>{

    List<OrderGetDto> findAllByStatus(OrderStatus status);
}

package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.OrderBookCreateDto;
import by.ginel.weblib.dto.OrderBookGetDto;
import by.ginel.weblib.dto.OrderBookUpdateDto;

import java.util.List;

public interface OrderBookService extends Service<OrderBookCreateDto, OrderBookUpdateDto, OrderBookGetDto> {

    List<OrderBookGetDto> getAllByOrderId(Long id);
}

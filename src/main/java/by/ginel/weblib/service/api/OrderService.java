package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.*;
import by.ginel.weblib.entity.CartBook;

import java.util.List;

public interface OrderService extends Service<OrderCreateDto, OrderUpdateDto, OrderGetDto> {

    List<OrderGetDto> getAllByPersonId(Long id);

    void updateStatus(OrderUpdateDto newOrder);

    void placeOrder(PersonGetDto person, List<CartBook> cart);

    List<OrderGetDto> getAllByUser(PersonGetDto person);

    List<BookGetDto> getBooksFromOrder(Long id, List<OrderGetDto> orders);

    boolean removeOrder(Long id, List<OrderGetDto> orders);
}
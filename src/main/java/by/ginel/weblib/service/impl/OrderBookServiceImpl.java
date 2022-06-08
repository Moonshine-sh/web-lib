package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.dao.api.OrderBookDao;
import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.entity.Order;
import by.ginel.weblib.entity.OrderBook;
import by.ginel.weblib.service.api.OrderBookService;
import by.ginel.weblib.dto.OrderBookCreateDto;
import by.ginel.weblib.dto.OrderBookGetDto;
import by.ginel.weblib.dto.OrderBookUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderBookServiceImpl implements OrderBookService {

    @Autowired
    OrderBookDao orderBookDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    BookDao bookDao;

    @Transactional
    @Override
    public OrderBookGetDto save(OrderBookCreateDto orderBookCreateDto) {
        OrderBook orderBook = orderBookDao.save(
                OrderBook.builder()
                        .quantity(orderBookCreateDto.getQuantity())
                        .book(bookDao.getById(orderBookCreateDto.getBookId()))
                        .order(orderDao.getById(orderBookCreateDto.getOrderId()))
                        .build()
        );
        return OrderBookGetDto.builder()
                .id(orderBook.getId())
                .quantity(orderBook.getQuantity())
                .bookId(orderBook.getBook().getId())
                .orderId(orderBook.getOrder().getId())
                .build();
    }

    @Transactional
    @Override
    public void delete(Long id) { orderBookDao.delete(id);
    }

    @Transactional
    @Override
    public void update(OrderBookUpdateDto orderBookUpdateDto) {
        OrderBook orderBook = new OrderBook();
        orderBook.setId(orderBookUpdateDto.getId());
        orderBook.setQuantity(orderBookUpdateDto.getQuantity());
        orderBook.setBook(bookDao.getById(orderBookUpdateDto.getBookId()));
        orderBook.setOrder(orderDao.getById(orderBookUpdateDto.getOrderId()));

        orderBookDao.update(orderBook);
    }

    @Override
    public OrderBookGetDto getById(Long id) {
        OrderBook orderBook = orderBookDao.getById(id);
        return  OrderBookGetDto.builder()
                .id(orderBook.getId())
                .quantity(orderBook.getQuantity())
                .bookId(orderBook.getBook().getId())
                .orderId(orderBook.getOrder().getId())
                .build();
    }

    @Override
    public List<OrderBookGetDto> getAll() {
        List<OrderBook> orderBooks = orderBookDao.getAll();
        return orderBooks
                .stream()
                .map(orderBook ->OrderBookGetDto.builder()
                        .id(orderBook.getId())
                        .quantity(orderBook.getQuantity())
                        .bookId(orderBook.getBook().getId())
                        .orderId(orderBook.getOrder().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderBookGetDto> getAllByOrderId(Long id) {
        log.info("Executing method getAllByOrderId()");
        List<OrderBook> orderBooks = orderBookDao.getAll();
        return orderBooks
                .stream()
                .filter(orderBook -> orderBook.getOrder().getId().equals(id))
                .map(orderBook ->OrderBookGetDto.builder()
                        .id(orderBook.getId())
                        .quantity(orderBook.getQuantity())
                        .bookId(orderBook.getBook().getId())
                        .orderId(orderBook.getOrder().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.dao.api.StatusDao;
import by.ginel.weblib.dto.*;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.OrderBook;
import by.ginel.weblib.entity.Orders;
import by.ginel.weblib.mapper.BookMapper;
import by.ginel.weblib.mapper.OrderBookMapper;
import by.ginel.weblib.mapper.OrderMapper;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.service.api.OrderBookService;
import by.ginel.weblib.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    protected final OrderMapper orderMapper;
    private final OrderBookService orderBookService;
    protected final OrderBookMapper orderBookMapper;
    private final BookService bookService;
    private final StatusDao statusDao;
    private final PersonRoleDao roleDao;
    private final BookMapper bookMapper;

    @Transactional
    @Override
    public OrderGetDto save(OrderCreateDto orderCreateDto) {
        Orders orders = orderDao.save(orderMapper.mapToOrder(orderCreateDto));
        return orderMapper.mapToOrderGetDto(orders);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Transactional
    @Override
    public void update(OrderUpdateDto orderUpdateDto) {
        orderDao.update(orderMapper.mapToOrder(orderUpdateDto));
    }

    @Override
    public OrderGetDto getById(Long id) throws NullPointerException {
        Orders orders = orderDao.getById(id);
        return orderMapper.mapToOrderGetDto(orders);
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Orders> orders = orderDao.getAll();
        return orders
                .stream()
                .map(orderMapper::mapToOrderGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderGetDto> getAllByPersonId(Long id) {
        log.info("Executing method getAllByPersonId()");
        List<Orders> orders = orderDao.getAll();
        return orders
                .stream()
                .filter(order -> order.getPerson().getId().equals(id))
                .map(orderMapper::mapToOrderGetDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(OrderUpdateDto newOrder) throws NullPointerException {
        log.info("Executing method updateStatus()");
        Orders orders = orderDao.getById(newOrder.getId());
        orders.setStatus(newOrder.getStatus().stream().map(statusDao::findByName).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void placeOrder(PersonGetDto person, List<CartBook> cart) {
        log.info("Executing method placeOrder()");
        OrderGetDto order = getNewOrder(person);

        cart.stream()
                .map(item -> {
                    OrderBookCreateDto orderBookCreateDto = orderBookMapper.mapToOrderBookCreateDto(item);
                    orderBookCreateDto.setOrderId(order.getId());
                    return orderBookCreateDto;
                })
                .collect(Collectors.toList())
                .forEach(orderBookService::save);

        order.setPrice(findOrderPrice(cart));
        update(orderMapper.mapToOrderUpdateDto(order));
    }

    @Override
    public List<OrderGetDto> getAllByUser(PersonGetDto person) {
        List<OrderGetDto> orders;
        if (person.getRole().contains(roleDao.findByName("ADMIN"))) {
            orders = getAll();
        } else {
            orders = getAllByPersonId(person.getId());
        }
        return orders;
    }

    @Override
    public List<BookGetDto> getBooksFromOrder(Long id, List<OrderGetDto> orders) {
        orders.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElseThrow();



        Orders order = orderDao.getById(id);
        List<BookGetDto> books = order.getBooks().stream().map(OrderBook::getBook).map(bookMapper::mapToBookGetDto).collect(Collectors.toList());

        return books;
    }

    @Override
    public boolean removeOrder(Long id, List<OrderGetDto> orders) {
        Optional<OrderGetDto> orderGetDto = orders.stream()
                .filter(order -> order.getId().equals(id) && order.getStatus().contains(statusDao.findByName("BOOKED").getName()))
                .findFirst();
        if (orderGetDto.isPresent()) {
            delete(orderGetDto.get().getId());
            return true;
        } else
            return false;
    }

    private Long findOrderPrice(List<CartBook> cart) {
        return cart.stream().mapToLong(
                item -> (long) (item.getQuantity() * bookService.getById(item.getBookId()).getPrice())
        ).sum();
    }

    private OrderGetDto getNewOrder(PersonGetDto person) {
        log.info("Executing method placeOrder()");
        OrderCreateDto order = OrderCreateDto.builder()
                .personId(person.getId())
                .date(new Date().toString())
                .status(List.of(statusDao.findByName("BOOKED").getName()))
                .build();
        return save(order);
    }
}

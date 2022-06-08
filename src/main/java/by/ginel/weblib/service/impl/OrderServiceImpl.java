package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Order;
import by.ginel.weblib.service.api.OrderService;
import by.ginel.weblib.dto.OrderCreateDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    PersonDao personDao;

    @Transactional
    @Override
    public OrderGetDto save(OrderCreateDto orderCreateDto) {
        Order order = orderDao.save(
                Order.builder()
                        .date(orderCreateDto.getDate())
                        .price(orderCreateDto.getPrice())
                        .person(personDao.getById(orderCreateDto.getPersonId()))
                        .orderStatus(orderCreateDto.getStatus())
                        .build()
        );
        return OrderGetDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .price(order.getPrice())
                .personId(order.getPerson().getId())
                .status(order.getOrderStatus().toString())
                .build();
    }

    @Transactional
    @Override
    public void delete(Long id) { orderDao.delete(id);
    }

    @Transactional
    @Override
    public void update(OrderUpdateDto orderUpdateDto) {

        Order order = new Order();
        order.setId(orderUpdateDto.getId());
        order.setDate(orderUpdateDto.getDate());
        order.setPrice(orderUpdateDto.getPrice());
        order.setPerson(personDao.getById(orderUpdateDto.getPersonId()));
        order.setOrderStatus(orderUpdateDto.getStatus());

        orderDao.update(order);
    }

    @Override
    public OrderGetDto getById(Long id) throws NullPointerException{
        Order order = orderDao.getById(id);
        return  OrderGetDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .price(order.getPrice())
                .personId(order.getPerson().getId())
                .status(order.getOrderStatus().toString())
                .build();
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderDao.getAll();
        return orders
                .stream()
                .map(order ->OrderGetDto.builder()
                        .id(order.getId())
                        .date(order.getDate())
                        .price(order.getPrice())
                        .personId(order.getPerson().getId())
                        .status(order.getOrderStatus().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderGetDto> getAllByPersonId(Long id) {
        log.info("Executing method getAllByPersonId()");
        List<Order> orders = orderDao.getAll();
        return orders
                .stream()
                .filter(order -> order.getPerson().getId().equals(id))
                .map(order ->OrderGetDto.builder()
                        .id(order.getId())
                        .date(order.getDate())
                        .price(order.getPrice())
                        .personId(order.getPerson().getId())
                        .status(order.getOrderStatus().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(OrderUpdateDto newOrder) throws NullPointerException {
        log.info("Executing method updateStatus()");
        Order order = orderDao.getById(newOrder.getId());
        order.setOrderStatus(newOrder.getStatus());
    }
}

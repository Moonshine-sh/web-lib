package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Order;
import by.ginel.weblib.entity.OrderStatus;
import by.ginel.weblib.service.api.OrderService;
import by.ginel.weblib.dto.OrderCreateDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    PersonDao personDao;

    @Transactional
    @Override
    public void save(OrderCreateDto orderCreateDto) {
        orderDao.save(
                Order.builder()
                        .date(orderCreateDto.getDate())
                        .person(personDao.getById(orderCreateDto.getPersonId()))
                        .status(orderCreateDto.getStatus())
                        .build()
        );
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
        order.setPerson(personDao.getById(orderUpdateDto.getPersonId()));
        order.setStatus(orderUpdateDto.getStatus());

        orderDao.update(order);
    }

    @Override
    public OrderGetDto getById(Long id) {
        Order order = orderDao.getById(id);
        return  OrderGetDto.builder()
                .id(order.getId())
                .date(order.getDate().toString())
                .personId(order.getPerson().getId())
                .status(order.getStatus().toString())
                .build();
    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderDao.getAll();
        return orders
                .stream()
                .map(order ->OrderGetDto.builder()
                        .id(order.getId())
                        .date(order.getDate().toString())
                        .personId(order.getPerson().getId())
                        .status(order.getStatus().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderGetDto> findAllByStatus(OrderStatus status) {
        List<Order> orders = orderDao.findAllByStatus(status);
        return orders
                .stream()
                .map(order ->OrderGetDto.builder()
                        .id(order.getId())
                        .date(order.getDate().toString())
                        .personId(order.getPerson().getId())
                        .status(order.getStatus().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

package by.ginel.weblib.service;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dto.OrderUpdateDto;
import by.ginel.weblib.entity.*;
import by.ginel.weblib.service.api.OrderService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderDao orderDao;

    @Test
    public void getAllByPersonIdTest(){

        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();

        Person person1 = new Person();
        Person person2 = new Person();

        person1.setId(1L);
        person2.setId(2L);

        order1.setPerson(person1);
        order1.setOrderStatus(OrderStatus.BOOKED);
        order2.setPerson(person1);
        order2.setOrderStatus(OrderStatus.BOOKED);
        order3.setPerson(person2);
        order3.setOrderStatus(OrderStatus.BOOKED);
        order4.setPerson(person1);
        order4.setOrderStatus(OrderStatus.BOOKED);

        List<Order> orders = new ArrayList<>(Arrays.asList(order1,order2,order3,order4));

        Mockito.when(orderDao.getAll()).thenReturn(orders);

        Assertions.assertEquals(3, orderService.getAllByPersonId(1L).size());
    }
}

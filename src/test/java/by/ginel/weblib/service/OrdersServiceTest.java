package by.ginel.weblib.service;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dao.api.StatusDao;
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
public class OrdersServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    StatusDao statusDao;

    @MockBean
    OrderDao orderDao;

    @Test
    public void getAllByPersonIdTest(){

        Orders orders1 = new Orders();
        Orders orders2 = new Orders();
        Orders orders3 = new Orders();
        Orders orders4 = new Orders();

        Person person1 = new Person();
        Person person2 = new Person();

        person1.setId(1L);
        person2.setId(2L);

        orders1.setPerson(person1);
        orders1.setStatus(List.of(statusDao.findByName("BOOKED")));
        orders2.setPerson(person1);
        orders2.setStatus(List.of(statusDao.findByName("BOOKED")));
        orders3.setPerson(person2);
        orders3.setStatus(List.of(statusDao.findByName("BOOKED")));
        orders4.setPerson(person1);
        orders4.setStatus(List.of(statusDao.findByName("BOOKED")));

        List<Orders> orders = new ArrayList<>(Arrays.asList(orders1, orders2, orders3, orders4));

        Mockito.when(orderDao.getAll()).thenReturn(orders);

        Assertions.assertEquals(3, orderService.getAllByPersonId(1L).size());
    }
}

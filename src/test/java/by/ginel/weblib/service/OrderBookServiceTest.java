package by.ginel.weblib.service;

import by.ginel.weblib.dao.api.OrderBookDao;
import by.ginel.weblib.entity.Book;
import by.ginel.weblib.entity.Order;
import by.ginel.weblib.entity.OrderBook;
import by.ginel.weblib.service.api.OrderBookService;
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
public class OrderBookServiceTest {

    @Autowired
    OrderBookService orderBookService;

    @MockBean
    OrderBookDao orderBookDao;

    @Test
    public void getAllByOrderIdTest(){

        OrderBook orderBook1 = new OrderBook();
        OrderBook orderBook2 = new OrderBook();
        OrderBook orderBook3 = new OrderBook();
        OrderBook orderBook4 = new OrderBook();

        Order order1 = new Order();
        Order order2 = new Order();

        order1.setId(1L);
        order2.setId(2L);

        orderBook1.setOrder(order1);
        orderBook1.setBook(new Book());
        orderBook2.setOrder(order1);
        orderBook2.setBook(new Book());
        orderBook3.setOrder(order2);
        orderBook3.setBook(new Book());
        orderBook4.setOrder(order1);
        orderBook4.setBook(new Book());

        List<OrderBook> orderBooks = new ArrayList<>(Arrays.asList(orderBook1,orderBook2,orderBook3,orderBook4));

        Mockito.when(orderBookDao.getAll()).thenReturn(orderBooks);

        Assertions.assertEquals(3, orderBookService.getAllByOrderId(1L).size());
    }
}

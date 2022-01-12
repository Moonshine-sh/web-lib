package by.ginel.weblib.dao.api;

import by.ginel.weblib.dao.entity.Order;
import by.ginel.weblib.dao.entity.OrderStatus;

import java.util.List;

public interface OrderDao extends Dao<Order> {

    List<Order> findAllByStatus(OrderStatus status);
}

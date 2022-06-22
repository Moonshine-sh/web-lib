package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

}

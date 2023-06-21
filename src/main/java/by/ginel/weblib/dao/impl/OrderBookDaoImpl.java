package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.OrderBookDao;
import by.ginel.weblib.entity.OrderBook;
import org.springframework.stereotype.Repository;

@Repository
public class OrderBookDaoImpl extends AbstractDao<OrderBook> implements OrderBookDao {
    @Override
    protected Class<OrderBook> getEntityClass() {
        return OrderBook.class;
    }
}

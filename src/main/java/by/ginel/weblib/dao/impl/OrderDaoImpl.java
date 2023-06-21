package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderDaoImpl extends AbstractDao<Orders> implements OrderDao {

    @Override
    protected Class<Orders> getEntityClass() {
        return Orders.class;
    }

}

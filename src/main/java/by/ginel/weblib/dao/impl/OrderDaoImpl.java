package by.ginel.weblib.dao.impl;

import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.entity.Order;
import by.ginel.weblib.entity.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    @Override
    protected Class<Order> getEntityClass() { return Order.class; }

}

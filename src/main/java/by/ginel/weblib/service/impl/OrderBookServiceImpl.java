package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.OrderBookDao;
import by.ginel.weblib.dto.OrderBookCreateDto;
import by.ginel.weblib.dto.OrderBookGetDto;
import by.ginel.weblib.dto.OrderBookUpdateDto;
import by.ginel.weblib.entity.OrderBook;
import by.ginel.weblib.mapper.OrderBookMapper;
import by.ginel.weblib.service.api.OrderBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBookDao orderBookDao;
    protected final OrderBookMapper orderBookMapper;

    @Transactional
    @Override
    public OrderBookGetDto save(OrderBookCreateDto orderBookCreateDto) {
        OrderBook orderBook = orderBookDao.save(orderBookMapper.mapToOrderBook(orderBookCreateDto));
        return orderBookMapper.mapToOrderBookGetDto(orderBook);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderBookDao.delete(id);
    }

    @Transactional
    @Override
    public void update(OrderBookUpdateDto orderBookUpdateDto) {
        orderBookDao.update(orderBookMapper.mapToOrderBook(orderBookUpdateDto));
    }

    @Override
    public OrderBookGetDto getById(Long id) {
        OrderBook orderBook = orderBookDao.getById(id);
        return orderBookMapper.mapToOrderBookGetDto(orderBook);
    }

    @Override
    public List<OrderBookGetDto> getAll() {
        List<OrderBook> orderBooks = orderBookDao.getAll();
        return orderBooks
                .stream()
                .map(orderBookMapper::mapToOrderBookGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderBookGetDto> getAllByOrderId(Long id) {
        log.info("Executing method getAllByOrderId()");
        List<OrderBook> orderBooks = orderBookDao.getAll();
        return orderBooks
                .stream()
                .filter(orderBook -> orderBook.getOrders().getId().equals(id))
                .map(orderBookMapper::mapToOrderBookGetDto)
                .collect(Collectors.toList());
    }
}

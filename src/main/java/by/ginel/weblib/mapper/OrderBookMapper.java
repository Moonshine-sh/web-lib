package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.BookDao;
import by.ginel.weblib.dao.api.OrderDao;
import by.ginel.weblib.dto.OrderBookCreateDto;
import by.ginel.weblib.dto.OrderBookGetDto;
import by.ginel.weblib.dto.OrderBookUpdateDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.OrderBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderBookMapper {

    @Autowired
    protected BookDao bookDao;
    @Autowired
    protected OrderDao orderDao;

    @Mappings({
            @Mapping(target = "bookId", expression = "java(orderBook.getBook().getId())"),
            @Mapping(target = "orderId", expression = "java(orderBook.getOrder().getId())")
    })
    public abstract OrderBookGetDto mapToOrderBookGetDto(OrderBook orderBook);

    public abstract OrderBookCreateDto mapToOrderBookCreateDto(CartBook cartBook);

    @Mappings({
            @Mapping(target = "book", expression = "java(bookDao.getById(orderBookCreateDto.getBookId()))"),
            @Mapping(target = "order", expression = "java(orderDao.getById(orderBookCreateDto.getOrderId()))")
    })
    public abstract OrderBook mapToOrderBook(OrderBookCreateDto orderBookCreateDto);

    @Mappings({
            @Mapping(target = "book", expression = "java(bookDao.getById(orderBookUpdateDto.getBookId()))"),
            @Mapping(target = "order", expression = "java(orderDao.getById(orderBookUpdateDto.getOrderId()))")
    })
    public abstract OrderBook mapToOrderBook(OrderBookUpdateDto orderBookUpdateDto);
}

package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dto.OrderCreateDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;
import by.ginel.weblib.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    protected PersonDao personDao;

    @Mappings({
            @Mapping(target = "personId", expression = "java(order.getPerson().getId())"),
            @Mapping(target = "status", expression = "java(order.getOrderStatus().toString())")
    })
    public abstract OrderGetDto mapToOrderGetDto(Order order);

    public abstract OrderUpdateDto mapToOderUpdateDto(OrderGetDto orderGetDto);

    @Mappings({
            @Mapping(target = "person", expression = "java(personDao.getById(orderCreateDto.getPersonId()))"),
            @Mapping(target = "orderStatus", expression = "java(orderCreateDto.getStatus())")
    })
    public abstract Order mapToOrder(OrderCreateDto orderCreateDto);

    @Mappings({
            @Mapping(target = "person", expression = "java(personDao.getById(orderUpdateDto.getPersonId()))"),
            @Mapping(target = "orderStatus", expression = "java(orderUpdateDto.getStatus())"),
            @Mapping(target = "id", expression = "java(orderUpdateDto.getId())")
    })
    public abstract Order mapToOrder(OrderUpdateDto orderUpdateDto);
}

package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dao.api.StatusDao;
import by.ginel.weblib.dto.OrderCreateDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;
import by.ginel.weblib.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public abstract class OrderMapper {

    @Autowired
    protected PersonDao personDao;
    @Autowired
    protected StatusDao statusDao;

    @Mappings({
            @Mapping(target = "personId", expression = "java(orders.getPerson().getId())"),
            @Mapping(target = "status", expression = "java(orders.getStatus().stream().map(status -> String.valueOf(status.getName())).collect(Collectors.toList()))")
    })
    public abstract OrderGetDto mapToOrderGetDto(Orders orders);

    public abstract OrderUpdateDto mapToOrderUpdateDto(OrderGetDto orderGetDto);

    @Mappings({
            @Mapping(target = "person", expression = "java(personDao.getById(orderCreateDto.getPersonId()))"),
            @Mapping(target = "status", expression = "java(orderCreateDto.getStatus().stream().map(statusDao::findByName).collect(Collectors.toList()))")
    })
    public abstract Orders mapToOrder(OrderCreateDto orderCreateDto);

    @Mappings({
            @Mapping(target = "person", expression = "java(personDao.getById(orderUpdateDto.getPersonId()))"),
            @Mapping(target = "status", expression = "java(orderUpdateDto.getStatus().stream().map(statusDao::findByName).collect(Collectors.toList()))"),
            @Mapping(target = "id", expression = "java(orderUpdateDto.getId())")
    })
    public abstract Orders mapToOrder(OrderUpdateDto orderUpdateDto);
}

package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public abstract class PersonMapper {

    @Autowired
    protected PersonRoleDao personRoleDao;

    @Mapping(target = "role", expression = "java(person.getRole().stream().map(role -> String.valueOf(role.getName())).collect(Collectors.toList()))")
    public abstract PersonGetDto mapToPersonGetDto(Person person);

    public abstract PersonUpdateDto mapToPersonUpdateDto(PersonGetDto personGetDto);

    @Mapping(target = "role", expression = "java(personCreateDto.getRole().stream().map(personRoleDao::findByName).collect(Collectors.toList()))")
    public abstract Person mapToPerson(PersonCreateDto personCreateDto);

    @Mapping(target = "role", expression = "java(personUpdateDto.getRole().stream().map(personRoleDao::findByName).collect(Collectors.toList()))")
    public abstract Person mapToPerson(PersonUpdateDto personUpdateDto);
}
package by.ginel.weblib.mapper;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonGetDto mapToPersonGetDto(Person person);

    Person mapToPerson(PersonCreateDto personCreateDto);

    Person mapToPerson(PersonUpdateDto personUpdateDto);
}

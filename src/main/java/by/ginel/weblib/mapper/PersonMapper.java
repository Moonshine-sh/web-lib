package by.ginel.weblib.mapper;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract PersonGetDto mapToPersonGetDto(Person person);

    public abstract PersonUpdateDto mapToPersonUpdateDto(PersonGetDto personGetDto);


    @Mapping(target = "password", expression = "java(passwordEncoder.encode(personCreateDto.getPassword()))")
    public abstract Person mapToPerson(PersonCreateDto personCreateDto);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(personUpdateDto.getPassword()))")
    public abstract Person mapToPerson(PersonUpdateDto personUpdateDto);
}

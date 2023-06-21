package by.ginel.weblib.mapper;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dto.*;
import by.ginel.weblib.entity.PersonCred;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class PersonCredMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected PersonDao personDao;

    @Mapping(target = "personId", expression = "java(personCred.getPerson().getId())")
    public abstract PersonCredGetDto mapToPersonCredGetDto(PersonCred personCred);

    public abstract PersonCredUpdateDto mapToPersonCredUpdateDto(PersonCredGetDto personCredGetDto);

    @Mappings({
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(personCredCreateDto.getPassword()))"),
            @Mapping(target = "person", expression = "java(personDao.getById(personCredCreateDto.getPersonId()))")
    })
    public abstract PersonCred mapToPersonCred(PersonCredCreateDto personCredCreateDto);

    @Mapping(target = "person", expression = "java(personDao.getById(personCredUpdateDto.getPersonId()))")
    public abstract PersonCred mapToPersonCred(PersonCredUpdateDto personCredUpdateDto);
}
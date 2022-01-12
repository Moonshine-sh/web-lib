package by.ginel.weblib.service.api;

import by.ginel.weblib.service.dto.PersonCreateDto;
import by.ginel.weblib.service.dto.PersonGetDto;
import by.ginel.weblib.service.dto.PersonUpdateDto;

import java.util.List;

public interface PersonService extends Service<PersonCreateDto, PersonUpdateDto, PersonGetDto>{

    List<PersonGetDto> findAllByName(String name);
    List<PersonGetDto> findAllLocked();
    List<PersonGetDto> findByLogin(String login);
}

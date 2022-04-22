package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;

import java.util.List;

public interface PersonService extends Service<PersonCreateDto, PersonUpdateDto, PersonGetDto>{

    List<PersonGetDto> findAllByName(String name);
    List<PersonGetDto> findAllLocked();
    PersonGetDto findByLogin(String login);
    void updateLocked(Long id);
}

package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PersonService extends Service<PersonCreateDto, PersonUpdateDto, PersonGetDto>{

    List<PersonGetDto> findAllByName(String name);
    List<PersonGetDto> findAllLocked();
    PersonGetDto findByLogin(String login);
    void updateLocked(Long id);
    boolean isUsersEmpty();
    boolean isAdmin(Long id);
    boolean isValidCred(String login, String password, HttpServletRequest request);
    boolean isUserValid(PersonCreateDto person);
}

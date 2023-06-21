package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.*;

import javax.servlet.http.HttpServletRequest;


public interface PersonCredService extends Service<PersonCredCreateDto, PersonCredUpdateDto, PersonCredGetDto>{

    PersonCredGetDto findByLogin(String login);

    void updateLocked(Long id);

    void activateUser(Long id) throws NullPointerException;

    boolean isValidCred(String login, String password, HttpServletRequest request);

    PersonGetDto isUserValid(PersonCreateDto person, PersonCredCreateDto personCred);
}

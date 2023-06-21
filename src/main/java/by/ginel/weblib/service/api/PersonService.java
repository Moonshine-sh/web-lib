package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.LockUserDto;
import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.VerificationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PersonService extends Service<PersonCreateDto, PersonUpdateDto, PersonGetDto> {

    List<PersonGetDto> findAllByName(String name);

    boolean isUsersEmpty();

    boolean isAdmin(Long id);

    PersonGetDto getUserByToken(String token);

    VerificationToken getVerificationToken(String token);

    void createVerificationToken(PersonGetDto personGetDto, String token);

    List<LockUserDto> getAllLockUsers();
}

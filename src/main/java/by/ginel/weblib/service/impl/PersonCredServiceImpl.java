package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.GenreDao;
import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.dto.*;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonCred;
import by.ginel.weblib.mapper.PersonCredMapper;
import by.ginel.weblib.mapper.PersonMapper;
import by.ginel.weblib.service.api.PersonCredService;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonCredServiceImpl implements PersonCredService {

    private final PersonCredDao personCredDao;
    private final PersonCredMapper personCredMapper;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonDao personDao;
    private final PersonRoleDao personRoleDao;
    private final PersonService personService;

    @Transactional
    @Override
    public PersonCredGetDto save(PersonCredCreateDto personCredCreateDto) {
        PersonCred personCred = personCredDao.save(personCredMapper.mapToPersonCred(personCredCreateDto));
        return personCredMapper.mapToPersonCredGetDto(personCred);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        personCredDao.delete(id);
    }

    @Transactional
    @Override
    public void update(PersonCredUpdateDto personCredUpdateDto) {
        personCredDao.update(personCredMapper.mapToPersonCred(personCredUpdateDto));
    }

    @Override
    public PersonCredGetDto getById(Long id) {
        PersonCred personCred = personCredDao.getById(id);
        return personCredMapper.mapToPersonCredGetDto(personCred);
    }

    @Override
    public List<PersonCredGetDto> getAll() {
        List<PersonCred> peopleCred = personCredDao.getAll();
        return peopleCred
                .stream()
                .map(personCredMapper::mapToPersonCredGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonCredGetDto findByLogin(String login) {
        log.info("Executing method findByLogin()");
        try {
            PersonCred personCred = personCredDao.findByLogin(login);
            return personCredMapper.mapToPersonCredGetDto(personCred);
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateLocked(Long id) throws NullPointerException {
        log.info("Executing method updateLocked()");
        PersonCred personCred = personDao.getById(id).getCredentials();
        personCred.setLocked(!personCred.getLocked());
    }

    @Override
    @Transactional
    public void activateUser(Long id) throws NullPointerException {
        log.info("Executing method updateEnable()");
        PersonCred personCred = personDao.getById(id).getCredentials();
        personCred.setEnabled(true);
    }

    @Override
    public boolean isValidCred(String login, String password, HttpServletRequest request) {
        log.info("Executing method isValidCred()", login, password);
        PersonCredGetDto personCred = findByLogin(login);
        if (personCred != null && passwordEncoder.matches(password, personCred.getPassword())) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(3600);
            session.setAttribute("person", personDao.getById(personCred.getPersonId()));
            return true;
        } else
            return false;
    }

    @Override
    public PersonGetDto isUserValid(PersonCreateDto person, PersonCredCreateDto personCred) {
        PersonCredGetDto personFromDB = findByLogin(personCred.getLogin());
        if (personFromDB == null) {
            personCred.setLocked(false);
            personCred.setEnabled(false);
            person.setRole(List.of(personRoleDao.findByName("USER").getName()));
            PersonGetDto personGetDto = personService.save(person);
            personCred.setPersonId(personGetDto.getId());
            save(personCred);
            return personGetDto;
        } else
            return null;
    }
}

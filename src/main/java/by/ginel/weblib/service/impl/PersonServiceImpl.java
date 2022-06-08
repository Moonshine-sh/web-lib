package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.PersonService;
import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Transactional
    @Override
    public PersonGetDto save(PersonCreateDto personCreateDto) {
        Person person = personDao.save(
                Person.builder()
                        .firstName(personCreateDto.getFirstName())
                        .lastName(personCreateDto.getLastName())
                        .locked(personCreateDto.getLocked())
                        .login(personCreateDto.getLogin())
                        .password(personCreateDto.getPassword())
                        .email(personCreateDto.getEmail())
                        .role(personCreateDto.getRole())
                        .build()
        );
        return PersonGetDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .locked(person.getLocked())
                .login(person.getLogin())
                .password(person.getPassword())
                .email(person.getEmail())
                .role(person.getRole().toString())
                .build();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        personDao.delete(id);
    }

    @Transactional
    @Override
    public void update(PersonUpdateDto personUpdateDto) {
        Person person = new Person();
        person.setId(personUpdateDto.getId());
        person.setFirstName(personUpdateDto.getFirstName());
        person.setLastName(personUpdateDto.getLastName());
        person.setLocked(personUpdateDto.getLocked());
        person.setLogin(personUpdateDto.getLogin());
        person.setPassword(personUpdateDto.getPassword());
        person.setEmail(personUpdateDto.getEmail());
        person.setRole(personUpdateDto.getRole());
        personDao.update(person);
    }

    @Override
    public PersonGetDto getById(Long id) {
        Person person = personDao.getById(id);
        return PersonGetDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .locked(person.getLocked())
                .login(person.getLogin())
                .password(person.getPassword())
                .email(person.getEmail())
                .role(person.getRole().toString())
                .build();
    }

    @Override
    public List<PersonGetDto> getAll() {
        List<Person> people = personDao.getAll();
        return people
                .stream()
                .map(person -> PersonGetDto.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .locked(person.getLocked())
                        .login(person.getLogin())
                        .password(person.getPassword())
                        .email(person.getEmail())
                        .role(person.getRole().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonGetDto> findAllByName(String name) {
        log.info("Executing method findAllByName()");
        List<Person> people = personDao.findAllByName(name);
        return people
                .stream()
                .map(person -> PersonGetDto.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .locked(person.getLocked())
                        .login(person.getLogin())
                        .password(person.getPassword())
                        .email(person.getEmail())
                        .role(person.getRole().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonGetDto> findAllLocked() {
        log.info("Executing method findAllLocked()");
        List<Person> people = personDao.findAllLocked();
        return people
                .stream()
                .map(person -> PersonGetDto.builder()
                        .id(person.getId())
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .locked(person.getLocked())
                        .login(person.getLogin())
                        .password(person.getPassword())
                        .email(person.getEmail())
                        .role(person.getRole().toString())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public PersonGetDto findByLogin(String login) {
        log.info("Executing method findByLogin()");
        try {
            Person person = personDao.findByLogin(login);
            return PersonGetDto.builder()
                    .id(person.getId())
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .locked(person.getLocked())
                    .login(person.getLogin())
                    .password(person.getPassword())
                    .email(person.getEmail())
                    .role(person.getRole().toString())
                    .build();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateLocked(Long id) throws NullPointerException {
        log.info("Executing method updateLocked()");
        Person person = personDao.getById(id);
        person.setLocked(!person.getLocked());
    }

    @Override
    public boolean isUsersEmpty() {
        List<Person> users = personDao.getAll();
        return users == null || users.size() == 0;
    }

    @Override
    public boolean isAdmin(Long id) {
        Person user = personDao.getById(id);
        return user.getRole() == PersonRole.ADMIN;
    }

    @Override
    public boolean isValidCred(String login, String password, HttpServletRequest request) {
        PersonGetDto person = findByLogin(login);
        if (person != null && person.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(3600);
            session.setAttribute("person", person);
            return true;
        } else
            return false;

    }

    @Override
    public boolean isUserValid(PersonCreateDto person) {
        PersonGetDto personFromDB = findByLogin(person.getLogin());
        if (personFromDB == null) {
            person.setLocked(false);
            person.setRole(PersonRole.USER.toString());
            save(person);
            return true;
        } else
            return false;
    }
}

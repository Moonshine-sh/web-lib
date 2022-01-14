package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.service.api.PersonService;
import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Transactional
    @Override
    public void save(PersonCreateDto personCreateDto) {
        personDao.save(
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
    }

    @Transactional
    @Override
    public void delete(Long id) { personDao.delete(id);
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
        return  PersonGetDto.builder()
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
        try{
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
        }catch (NoResultException ex){
            return null;
        }

    }
}

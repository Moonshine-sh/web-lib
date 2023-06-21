package by.ginel.weblib.service.impl;

import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.dao.api.VerificationTokenDao;
import by.ginel.weblib.dto.LockUserDto;
import by.ginel.weblib.dto.PersonCreateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.dto.PersonUpdateDto;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonCred;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.entity.VerificationToken;
import by.ginel.weblib.mapper.PersonMapper;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    protected final PersonMapper personMapper;
    private final VerificationTokenDao tokenDao;
    private final PersonRoleDao personRoleDao;

    @Transactional
    @Override
    public PersonGetDto save(PersonCreateDto personCreateDto) {
        Person person = personDao.save(personMapper.mapToPerson(personCreateDto));
        return personMapper.mapToPersonGetDto(person);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        personDao.delete(id);
    }

    @Transactional
    @Override
    public void update(PersonUpdateDto personUpdateDto) {
        personDao.update(personMapper.mapToPerson(personUpdateDto));
    }

    @Override
    public PersonGetDto getById(Long id) {
        Person person = personDao.getById(id);
        return personMapper.mapToPersonGetDto(person);
    }

    @Override
    public List<PersonGetDto> getAll() {
        List<Person> people = personDao.getAll();
        return people
                .stream()
                .map(personMapper::mapToPersonGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonGetDto> findAllByName(String name) {
        log.info("Executing method findAllByName()");
        List<Person> people = personDao.findAllByName(name);
        return people
                .stream()
                .map(personMapper::mapToPersonGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUsersEmpty() {
        List<Person> users = personDao.getAll();
        return users == null || users.size() == 0;
    }

    @Override
    public boolean isAdmin(Long id) {
        Person user = personDao.getById(id);
        return user.getRole().contains(personRoleDao.findByName("ADMIN"));
    }

    @Override
    public PersonGetDto getUserByToken(String token) {
        Person person = tokenDao.findByToken(token).getUser();
        return personMapper.mapToPersonGetDto(person);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public void createVerificationToken(PersonGetDto personGetDto, String token) {
        VerificationToken verificationToken = new VerificationToken(token, personDao.getById(personGetDto.getId()));
        tokenDao.save(verificationToken);
    }

    @Override
    public List<LockUserDto> getAllLockUsers(){
        List<LockUserDto> lockUsers = new ArrayList<>();
        List<Person> users = personDao.getAll();
        for (Person user : users) {
            LockUserDto lockUserDto = LockUserDto.builder()
                                           .id(user.getId())
                                           .firstName(user.getFirstName())
                                           .lastName(user.getLastName())
                                           .email(user.getEmail())
                                           .mobNum(user.getMobNum())
                                           .locked(user.getCredentials().getLocked())
                                           .build();
            lockUsers.add(lockUserDto);
        }
        return lockUsers;
    }
}

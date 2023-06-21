package by.ginel.weblib.dao;

import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonCred;
import by.ginel.weblib.entity.PersonRole;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonDaoTest {

    @Autowired
    PersonCredDao personCredDao;
    @Autowired
    PersonRoleDao personRoleDao;

    @Test
    public void findByLogin(){

        PersonCred personCred = new PersonCred();
        personCred.setLogin("123");
        personCred.setPassword("321");
        personCred.getPerson().setRole(List.of(personRoleDao.findByName("USER")));

        PersonCred newPersonCred = personCredDao.save(personCred);

        Assertions.assertEquals(newPersonCred.getId(), personCredDao.findByLogin(personCred.getLogin()).getId());
    }
}

package by.ginel.weblib.service;

import by.ginel.weblib.dao.api.PersonCredDao;
import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.dao.api.PersonRoleDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonCred;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.PersonService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @Autowired
    PersonService personService;
    @Autowired
    PersonRoleDao personRoleDao;

    @MockBean
    PersonCredDao personCredDao;

    @Test
    public void findByLoginTest(){

        PersonCred personCred = new PersonCred();
        personCred.setLogin("123");
        personCred.setPassword("321");
        personCred.getPerson().setRole(List.of(personRoleDao.findByName("USER")));

        Mockito.when(personCredDao.findByLogin("123")).thenReturn(personCred);
        Mockito.when(personCredDao.findByLogin("1")).thenReturn(null);

        Assertions.assertEquals(null, personCredDao.findByLogin("1"));

        Assertions.assertEquals("321", personCredDao.findByLogin("123").getPassword());
    }
}

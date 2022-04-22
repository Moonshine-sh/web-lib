package by.ginel.weblib.service;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @MockBean
    PersonDao personDao;

    @Test
    public void findByLoginTest(){

        Person person = new Person();
        person.setLogin("123");
        person.setPassword("321");
        person.setRole(PersonRole.USER);

        Mockito.when(personDao.findByLogin("123")).thenReturn(person);
        Mockito.when(personDao.findByLogin("1")).thenReturn(null);

        Assertions.assertEquals(null, personDao.findByLogin("1"));

        Assertions.assertEquals("321", personDao.findByLogin("123").getPassword());
    }
}

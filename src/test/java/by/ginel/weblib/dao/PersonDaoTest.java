package by.ginel.weblib.dao;

import by.ginel.weblib.dao.api.PersonDao;
import by.ginel.weblib.entity.Person;
import by.ginel.weblib.entity.PersonRole;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonDaoTest {

    @Autowired
    PersonDao personDao;

    @Test
    public void findByLogin(){

        Person person = new Person();
        person.setLogin("123");
        person.setPassword("321");
        person.setRole(PersonRole.USER);

        Person newPerson = personDao.save(person);

        Assertions.assertEquals(newPerson.getId(), personDao.findByLogin(person.getLogin()).getId());
    }
}

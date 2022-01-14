package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.Person;

import java.util.List;

public interface PersonDao extends Dao<Person>{

    List<Person> findAllByName(String name);
    List<Person> findAllLocked();
    Person findByLogin(String login);
}

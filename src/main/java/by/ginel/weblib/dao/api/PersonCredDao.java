package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.PersonCred;

public interface PersonCredDao extends Dao<PersonCred>{

    PersonCred findByLogin(String login);
}

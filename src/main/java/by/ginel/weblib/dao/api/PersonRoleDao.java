package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.PersonRole;

public interface PersonRoleDao extends Dao<PersonRole>{

    PersonRole findByName(String name);
}

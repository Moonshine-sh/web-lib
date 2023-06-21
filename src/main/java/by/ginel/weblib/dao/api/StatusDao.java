package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.Status;

public interface StatusDao extends Dao<Status>{

    Status findByName(String name);
}

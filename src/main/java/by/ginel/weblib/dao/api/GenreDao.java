package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.Genre;

public interface GenreDao extends Dao<Genre>{

    Genre findByName(String name);
}

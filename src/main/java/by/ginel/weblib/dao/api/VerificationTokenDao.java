package by.ginel.weblib.dao.api;

import by.ginel.weblib.entity.VerificationToken;

import javax.persistence.NoResultException;

public interface VerificationTokenDao extends Dao<VerificationToken> {
    VerificationToken findByToken(String token) throws NoResultException;
}

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.UserBank;
import de.htw.sdf.photoplatform.repository.UserBankDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Created by Sergej Meister.
 */
@Repository
@Transactional
public class UserBankDAOImpl extends GenericDAOImpl<UserBank> implements
        UserBankDAO {

    /**
     * Default constructor.
     */
    public UserBankDAOImpl() {
        super();
        setClazz(UserBank.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBank findByUserId(final Long id) {
        String queryString = "SELECT userBank FROM UserBank userBank "
                + "LEFT JOIN FETCH userBank.user user " + "WHERE user.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return (UserBank) query.getSingleResult();
    }
}

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.persistence.model.UserImage;
import de.htw.sdf.photoplatform.repository.UserImageDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository methods for relation between user and image.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class UserImageDAOImpl extends GenericDAOImpl<UserImage> implements
        UserImageDAO {

    /**
     * UserImage DAO constructor.
     */
    public UserImageDAOImpl() {
        super();
        setClazz(UserImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserImage> getUserImagesBy(User user) {
        StringBuilder queryBuilder = initSelectQuery();
        queryBuilder.append("WHERE user.id = ?1");
        Query query = createQuery(queryBuilder.toString());
        query.setParameter(1, user.getId());
        return (List<UserImage>) query.getResultList();
    }

    private StringBuilder initSelectQuery() {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT(userImage) FROM UserImage userImage ");
        queryBuilder.append("LEFT JOIN FETCH userImage.image image ");
        queryBuilder.append("LEFT JOIN FETCH userImage.user user ");

        return queryBuilder;
    }
}

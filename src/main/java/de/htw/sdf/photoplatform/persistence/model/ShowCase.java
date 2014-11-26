/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class for a showcase representing the corresponding database table.
 *
 * @author by Sergej Meister.
 */
@Entity
@Table(name = "RES_SHOWCASE")
public class ShowCase extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = 1985899279694176344L;

    /**
     * User.
     */
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    /**
     * Returns show case owner.
     *
     * @return user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets show case owner.
     *
     * @param user
     *            user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ShowCase{" + "user=" + user + '}';
    }
}

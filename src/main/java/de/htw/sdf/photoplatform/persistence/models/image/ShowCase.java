/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;
import de.htw.sdf.photoplatform.persistence.models.user.User;

/**
 * Entity class for a showcase representing the corresponding database table.
 *
 * @autor by Sergej Meister.
 */
@Entity
@Table(name = "RES_SHOWCASE")
public class ShowCase extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 1985899279694176344L;

    /**
     * User.
     */
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    /**
     * ShowCase collections.
     *
     * FetchType.EAGER default.
     */
    @OneToMany(mappedBy = "showCase")
    private Set<ShowCaseCollection> showCaseCollections;

    /**
     * Returns show case owner.
     *
     * @return user.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets show case owner.
     *
     * @param user
     *            user.
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Returns all collection in showCase.
     *
     * @return list of showCase collections.
     */
    public Set<ShowCaseCollection> getShowCaseCollections()
    {
        return showCaseCollections;
    }

    /**
     * Sets showCase collections.
     *
     * @param showCaseCollections
     *            showCase collections.
     */
    public void setShowCaseCollections(Set<ShowCaseCollection> showCaseCollections)
    {
        this.showCaseCollections = showCaseCollections;
    }
}

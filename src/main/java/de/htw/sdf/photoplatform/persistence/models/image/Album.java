/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;
import de.htw.sdf.photoplatform.persistence.models.user.User;

/**
 * Entity class for a album representing the corresponding database table.
 *
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "RES_ALBUM")
public class Album extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = -5509450928421280686L;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Returns album owner.
     *
     * @return user.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets album owner.
     *
     * @param user
     *            owner.
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Returns album name.
     *
     * @return album name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets album name.
     *
     * @param name
     *            album name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns album description.
     *
     * @return album description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets album decription.
     *
     * @param description
     *            album description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "Album{" + "user=" + user + ", name='" + name + '\'' + '}';
    }
}

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity class for a relation between user and image.
 *
 * @author Sergej Meister.
 */
@Entity
@Table(name = "RES_USER_IMAGE")
public class UserImage extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = -2454270353359397995L;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")
    private Image image;

    /**
     * Returns image owner.
     *
     * @return user.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets image owner.
     *
     * @param owner user.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Returns user.
     *
     * @return user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns image.
     *
     * @return image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

}

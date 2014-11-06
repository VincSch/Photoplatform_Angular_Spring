/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;
import de.htw.sdf.photoplatform.persistence.models.user.User;

/**
 * Entity class for a collection representing the corresponding database table.
 *
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "RES_COLLECTION")
public class Collection extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = -5509450928421280686L;

    /**
     * Image.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collection")
    Set<CollectionImage> collectionImages;

    /**
     * User.
     */
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    /**
     * Name.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * Description.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Key image for the collection.
     */
    @OneToOne
    @JoinColumn(name = "THUMBNAIL_ID", referencedColumnName = "ID")
    private Image thumbnail;

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
     * Returns key image for this collection.
     *
     * @return thumbnail.
     */
    public Image getThumbnail()
    {
        return thumbnail;
    }

    /**
     * Sets key image for this collection.
     *
     * @param thumbnail
     *            thumbnail.
     */
    public void setThumbnail(Image thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    /**
     * Returns all collection images.
     * 
     * @return collection images.
     */
    public Set<CollectionImage> getCollectionImages()
    {
        return collectionImages;
    }

    /**
     * Sets collection images.
     *
     * @param collectionImages
     *            list of images
     */
    public void setCollectionImages(Set<CollectionImage> collectionImages)
    {
        this.collectionImages = collectionImages;
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

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a collection representing the corresponding database table.
 *
 * @author Sergej Meister sergej_meister@gmx.net.
 */
@Entity
@Table(name = "RES_COLLECTION")
public class Collection extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = -5509450928421280686L;

    /**
     * User.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @JsonIgnore
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
     * Description.
     */
    @Column(name = "IS_PUBLIC", columnDefinition = "boolean default false")
    private boolean isPublic;

    /**
     * Key image for the collection.
     */
    @OneToOne
    @JoinColumn(name = "THUMBNAIL_ID", referencedColumnName = "ID")
    private Image thumbnail;

    /**
     * Image.
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collection")
    private Set<CollectionImage> collectionImages;

    /**
     * Category.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collection")
    private Set<CollectionCategory> collectionCategories;

    /**
     * Returns album owner.
     *
     * @return user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets album owner.
     *
     * @param user owner.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns album name.
     *
     * @return album name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets album name.
     *
     * @param name album name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns album description.
     *
     * @return album description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets album description.
     *
     * @param description album description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return is public
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * @param isPublic is public to set
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Returns key image for this collection.
     *
     * @return thumbnail.
     */
    public Image getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets key image for this collection.
     *
     * @param thumbnail thumbnail.
     */
    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Returns all collection images.
     *
     * @return collection images.
     */
    public Set<CollectionImage> getCollectionImages() {
        return collectionImages;
    }

    /**
     * Sets collection images.
     *
     * @param collectionImages list of images
     */
    public void setCollectionImages(Set<CollectionImage> collectionImages) {
        this.collectionImages = collectionImages;
    }

    /**
     * Returns list of collection categories.
     *
     * @return list of collection categories.
     */
    public Set<CollectionCategory> getCollectionCategories() {
        return collectionCategories;
    }

    /**
     * Sets collection categories.
     *
     * @param collectionCategories collection categories.
     */
    public void setCollectionCategories(
            Set<CollectionCategory> collectionCategories) {
        this.collectionCategories = collectionCategories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Collection{" + "user=" + user + ", name='" + name + '\'' + '}';
    }
}

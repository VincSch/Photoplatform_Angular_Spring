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
import javax.persistence.UniqueConstraint;

/**
 * Entity class for the relation between collection and images.
 * <p/>
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "RES_COLLECTION_IMAGE",
        uniqueConstraints = @UniqueConstraint(columnNames = {"COLLECTION_ID", "IMAGE_ID"}))
public class CollectionImage extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = -5332090503705930932L;

    @ManyToOne
    @JoinColumn(name = "COLLECTION_ID", referencedColumnName = "ID")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")
    private Image image;

    /**
     * Returns collection.
     *
     * @return collection.
     */
    public Collection getCollection() {
        return collection;
    }

    /**
     * Sets collection.
     *
     * @param collection
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    /**
     * Returns images.
     *
     * @return return image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Set image.
     *
     * @param image image.
     */
    public void setImage(Image image) {
        this.image = image;
    }
}

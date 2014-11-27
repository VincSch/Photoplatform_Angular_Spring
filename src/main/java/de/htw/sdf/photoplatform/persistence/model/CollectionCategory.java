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
 * Entity class for the relation between collection and category.
 *
 * @author Sergej Meister.
 */
@Entity
@Table(name = "RES_COLLECTION_CATEGORY")
public class CollectionCategory extends AbstractBaseAuditEntity {

    private static final long serialVersionUID = 4452905045009069139L;

    @ManyToOne
    @JoinColumn(name = "COLLECTION_ID", referencedColumnName = "ID")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    /**
     * Returns Collection.
     *
     * @return collection
     */
    public Collection getCollection() {
        return collection;
    }

    /**
     * Sets collection.
     *
     * @param collection collection
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    /**
     * Returns category.
     *
     * @return category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}

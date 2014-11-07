/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a relation between showcase and collection.
 *
 * @autor by Sergej Meister.
 */
@Entity
@Table(name = "RES_SHOWCASE_COLLECTION")
public class ShowCaseCollection extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 2186044812004220939L;

    @ManyToOne
    @JoinColumn(name = "SHOWCASE_ID", referencedColumnName = "ID")
    private ShowCase showCase;

    @ManyToOne
    @JoinColumn(name = "COLLECTION_ID", referencedColumnName = "ID")
    private Collection collection;

    /**
     * Returns showcase.
     *
     * @return showcase.
     */
    public ShowCase getShowCase()
    {
        return showCase;
    }

    /**
     * Sets showcase.
     *
     * @param showCase
     *            showCase.
     */
    public void setShowCase(ShowCase showCase)
    {
        this.showCase = showCase;
    }

    /**
     * Returns collection.
     * 
     * @return collection.
     */
    public Collection getCollection()
    {
        return collection;
    }

    /**
     * Sets collection.
     *
     * @param collection
     *            collection.
     */
    public void setCollection(Collection collection)
    {
        this.collection = collection;
    }
}

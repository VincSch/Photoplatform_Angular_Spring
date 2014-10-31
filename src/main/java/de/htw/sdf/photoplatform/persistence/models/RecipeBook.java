/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a recipe books corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_RECIPEBOOK")
public class RecipeBook extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = 316132395359811636L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    /**
     * Type.
     */
    public enum Type
    {
        /** Privat. */
        priv,
        /** Public. */
        pub;
    }

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeBook")
    private List<UsedRecipe> usedRecipes;

    /**
     * Recipe Book constructor.
     */
    public RecipeBook()
    {
        super();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the type
     */
    public Type getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * @return the used recipes
     */
    public List<UsedRecipe> getUsedRecipes()
    {
        return usedRecipes;
    }

    /**
     * @param usedRecipes
     *            the used recipes to set
     */
    public void setUsedRecipes(List<UsedRecipe> usedRecipes)
    {
        this.usedRecipes = usedRecipes;
    }

}

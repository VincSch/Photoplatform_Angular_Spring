/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a recipe books recipes corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_RECIPEBOOK_HAS_RECIPE")
public class UsedRecipe extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = -2140082125469640164L;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "RECIPEBOOK_ID", referencedColumnName = "ID")
    private RecipeBook recipeBook;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")
    private Recipe recipe;

    /**
     * Used recipe constructor.
     */
    public UsedRecipe()
    {
        super();
    }

    /**
     * @return the recipe book
     */
    public RecipeBook getRecipeBook()
    {
        return recipeBook;
    }

    /**
     * @param recipeBook
     *            the recipe book to set
     */
    public void setRecipeBook(RecipeBook recipeBook)
    {
        this.recipeBook = recipeBook;
    }

    /**
     * @return the recipe
     */
    public Recipe getRecipe()
    {
        return recipe;
    }

    /**
     * @param recipe
     *            the recipe to set
     */
    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }
}

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity mapping class for a recipe-ingredient representing the corresponding.
 * database table
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_USED_INGREDIENTS")
public class UsedIngredient extends BaseAuditEntity
{

    private static final long serialVersionUID = 8433747680888337645L;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID")
    private Ingredient ingredient;

    @Column(name = "AMOUNT")
    private double amount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")
    private Unit unit;

    /**
     * UsedIngredient constructor.
     */
    public UsedIngredient()
    {
        super();
    }

    /**
     * @return the name
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

    /**
     * @return the ingredient
     */
    public Ingredient getIngredient()
    {
        return ingredient;
    }

    /**
     * @param ingredient
     *            the ingredient to set
     */
    public void setIngredient(Ingredient ingredient)
    {
        this.ingredient = ingredient;
    }

    /**
     * @return the amount
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    /**
     * @return the unit
     */
    public Unit getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }
}

/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity mapping class for a nutrition fact of an ingredient and a specific amount representing the
 * corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "RB_NUTRITIONFACT")
public class NutritionFact extends AbstractBaseAuditEntity
{

    private static final long serialVersionUID = -768981160936776172L;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID")
    private Ingredient ingredient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")
    private Unit unit;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "CALORIES")
    private double calories;

    @Column(name = "CARBOHYDRATE")
    private double carbohydrate;

    @Column(name = "PROTEIN")
    private double protein;

    @Column(name = "FAT")
    private double fat;

    /**
     * @return the ingredient
     */
    public Ingredient getIngredient()
    {
        return ingredient;
    }

    /**
     * @param ingredient
     *            the ingredient
     */
    public void setIngredient(Ingredient ingredient)
    {
        this.ingredient = ingredient;
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
     * @return the calories
     */
    public double getCalories()
    {
        return calories;
    }

    /**
     * @param calories
     *            the calories to set
     */
    public void setCalories(double calories)
    {
        this.calories = calories;
    }

    /**
     * @return the carbohydrate
     */
    public double getCarbohydrate()
    {
        return carbohydrate;
    }

    /**
     * @param carbohydrate
     *            the carbohydrate to set
     */
    public void setCarbohydrate(double carbohydrate)
    {
        this.carbohydrate = carbohydrate;
    }

    /**
     * @return the protein
     */
    public double getProtein()
    {
        return protein;
    }

    /**
     * @param protein
     *            the protein to set
     */
    public void setProtein(double protein)
    {
        this.protein = protein;
    }

    /**
     * @return the fat
     */
    public double getFat()
    {
        return fat;
    }

    /**
     * @param fat
     *            the fat to set
     */
    public void setFat(double fat)
    {
        this.fat = fat;
    }
}

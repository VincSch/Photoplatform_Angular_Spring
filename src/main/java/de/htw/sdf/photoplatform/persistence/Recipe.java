/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.common.BaseAuditEntity;

/**
 * Entity class for a recipe representing the corresponding database table.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Entity
@Table(name = "RB_RECIPE")
public class Recipe extends BaseAuditEntity
{

    private static final long serialVersionUID = 1086441426865570028L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DIFFICULTY_ID", referencedColumnName = "ID")
    private RecipeDifficulty difficulty;

    @Column(name = "SERVES")
    private int serves;

    @Column(name = "PREPERATION_TIME")
    private String preperationTime;

    @Column(name = "COOKING_TIME")
    private String cookingTime;

    @Column(name = "PREPERATION")
    private String preperation;

    @Column(name = "NOTES")
    private String notes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    /** The Rating. */
    public enum Rating
    {
        /** Good. */
        good,

        /** Very Good. */
        very_good,

        /** Excellent. */
        excellent;
    }

    @Column(name = "RATING")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<UsedIngredient> usedIngredients;

    /**
     * Recipe constructor.
     */
    public Recipe()
    {
        super();
    }

    /**
     * Recipe constructor.
     *
     * @param name
     *            the name
     * @param difficulty
     *            the difficulty
     * @param serves
     *            the serves
     * @param preperationTime
     *            the preperationTime
     * @param cookingTime
     *            the cookingTime
     * @param preperation
     *            the preperation
     * @param notes
     *            the notes
     * @param rating
     *            the rating
     * @param ingredients
     *            the ingredients
     */
    public Recipe(
            String name,
            RecipeDifficulty difficulty,
            int serves,
            String preperationTime,
            String cookingTime,
            String preperation,
            String notes,
            Rating rating,
            List<Ingredient> ingredients)
    {
        setName(name);
        setDifficulty(difficulty);
        setServes(serves);
        setPreperation(preperation);
        setPreperationTime(preperationTime);
        setCookingTime(cookingTime);
        setNotes(notes);
        setRating(rating);
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
     * @return the serves
     */
    public int getServes()
    {
        return serves;
    }

    /**
     * @param serves
     *            the serves to set
     */
    public void setServes(int serves)
    {
        this.serves = serves;
    }

    /**
     * @return the preperation time
     */
    public String getPreperationTime()
    {
        return preperationTime;
    }

    /**
     * @param preperationTime
     *            the preperation time to set
     */
    public void setPreperationTime(String preperationTime)
    {
        this.preperationTime = preperationTime;
    }

    /**
     * @return the cooking time
     */
    public String getCookingTime()
    {
        return cookingTime;
    }

    /**
     * @param cookingTime
     *            the the cooking time to set
     */
    public void setCookingTime(String cookingTime)
    {
        this.cookingTime = cookingTime;
    }

    /**
     * @return the preperation
     */
    public String getPreperation()
    {
        return preperation;
    }

    /**
     * @param preperation
     *            the preperation to set
     */
    public void setPreperation(String preperation)
    {
        this.preperation = preperation;
    }

    /**
     * @return the notes
     */
    public String getNotes()
    {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    /**
     * @return the rating
     */
    public Rating getRating()
    {
        return rating;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(Rating rating)
    {
        this.rating = rating;
    }

    /**
     * @return the used ingredients
     */
    public List<UsedIngredient> getUsedIngredients()
    {
        return usedIngredients;
    }

    /**
     * @param recipeUsesIngredients
     *            the the used ingredients to set
     */
    public void setUsedIngredients(List<UsedIngredient> recipeUsesIngredients)
    {
        this.usedIngredients = recipeUsesIngredients;
    }

    /**
     * @return the difficulty
     */
    public RecipeDifficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(RecipeDifficulty difficulty)
    {
        this.difficulty = difficulty;
    }

}

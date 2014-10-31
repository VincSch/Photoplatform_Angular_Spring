/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.htw.sdf.photoplatform.manager.NutritionFactManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.persistence.NutritionFact;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.persistence.UsedIngredient;

/**
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Service
@Transactional
public class NutritionFactManagerImpl extends DAOReferenceCollector implements
        NutritionFactManager
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(NutritionFact entity)
    {
        nutritionDAO.create(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NutritionFact update(NutritionFact entity)
    {
        return nutritionDAO.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(NutritionFact entity)
    {
        nutritionDAO.delete(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NutritionFact findById(long id)
    {
        return nutritionDAO.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NutritionFact> findAll()
    {
        return nutritionDAO.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NutritionFact findByIngredientId(Long id)
    {
        return nutritionDAO.findByIngredientId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<String, Double> calculateCalories(
            List<UsedIngredient> usedIngredients)
    {
        double calories = 0;
        double carbohydrate = 0;
        double fat = 0;
        double protein = 0;

        for (UsedIngredient usedIngredient : usedIngredients)
        {
            double amount = usedIngredient.getAmount();
            Unit unit = usedIngredient.getUnit();
            Ingredient ingredient = usedIngredient.getIngredient();

            NutritionFact nutrition = findByIngredientId(ingredient.getId());
            calories += calculateCalories(amount, unit, nutrition);
            fat += calculateFat(amount, unit, nutrition);
            protein += calculateProtein(amount, unit, nutrition);
            carbohydrate += calculateCarbohydrate(amount, unit, nutrition);
        }
        HashMap<String, Double> nurtritionMap = new HashMap<String, Double>();
        nurtritionMap.put("calories", calories);
        nurtritionMap.put("carbohydrate", carbohydrate);
        nurtritionMap.put("fat", fat);
        nurtritionMap.put("protein", protein);
        return nurtritionMap;
    }

    /**
     * Calculates the calories.
     * 
     * @param amount
     *            the amount
     * @param unit
     *            the unit
     * @param nutrition
     *            the nutrition
     * 
     * @return calculated calories
     */
    private double calculateCalories(
            double amount,
            Unit unit,
            NutritionFact nutrition)
    {
        double calories = 0;

        if (unit.getGermanUnitName().equals(
                nutrition.getUnit().getGermanUnitName()))
        {
            calories = (amount * nutrition.getCalories());
        }
        else
        {
            if (unit.getGermanUnitName().equals(GermanUnitName.t))
            {
                calories = (nutrition.getCalories() / 1000000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.Kg))
            {
                calories = (nutrition.getCalories() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.mg))
            {
                calories = (nutrition.getCalories() / 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.ml))
            {
                calories = nutrition.getCalories() * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.l))
            {
                calories = (nutrition.getCalories() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.EL))
            {
                calories = (nutrition.getCalories() * 15) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.TL))
            {
                calories = (nutrition.getCalories() * 5) * amount;
            }
        }

        return calories;
    }

    /**
     * Calculate carbohydrate.
     * 
     * @param amount
     *            the the amount
     * @param unit
     *            the unit
     * @param nutrition
     *            the nutrition
     * 
     * @return calculated carbohydrate
     */
    private double calculateCarbohydrate(
            double amount,
            Unit unit,
            NutritionFact nutrition)
    {
        double carbohydrate = 0;

        if (unit.getGermanUnitName().equals(
                nutrition.getUnit().getGermanUnitName()))
        {
            carbohydrate = (amount * nutrition.getCarbohydrate());
        }
        else
        {
            if (unit.getGermanUnitName().equals(GermanUnitName.t))
            {
                carbohydrate = (nutrition.getCarbohydrate() / 1000000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.Kg))
            {
                carbohydrate = (nutrition.getCarbohydrate() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.mg))
            {
                carbohydrate = (nutrition.getCarbohydrate() / 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.ml))
            {
                carbohydrate = nutrition.getCarbohydrate() * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.l))
            {
                carbohydrate = (nutrition.getCarbohydrate() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.EL))
            {
                carbohydrate = (nutrition.getCarbohydrate() * 15) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.TL))
            {
                carbohydrate = (nutrition.getCarbohydrate() * 5) * amount;
            }
        }

        return carbohydrate;
    }

    /**
     * Calculate fat.
     * 
     * @param amount
     *            the amount
     * @param unit
     *            the unit
     * @param nutrition
     *            the nutrition
     * 
     * @return calculated fat
     */
    private double calculateFat(
            double amount,
            Unit unit,
            NutritionFact nutrition)
    {
        double fat = 0;

        if (unit.getGermanUnitName().equals(
                nutrition.getUnit().getGermanUnitName()))
        {
            fat = (amount * nutrition.getFat());
        }
        else
        {
            if (unit.getGermanUnitName().equals(GermanUnitName.t))
            {
                fat = (nutrition.getFat() / 1000000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.Kg))
            {
                fat = (nutrition.getFat() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.mg))
            {
                fat = (nutrition.getFat() / 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.ml))
            {
                fat = nutrition.getFat() * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.l))
            {
                fat = (nutrition.getFat() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.EL))
            {
                fat = (nutrition.getFat() * 15) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.TL))
            {
                fat = (nutrition.getFat() * 5) * amount;
            }
        }

        return fat;
    }

    /**
     * Calculate protein.
     * 
     * @param amount
     *            the amount
     * @param unit
     *            the unit
     * @param nutrition
     *            the nutrition
     * 
     * @return calculated protein
     */
    private double calculateProtein(
            double amount,
            Unit unit,
            NutritionFact nutrition)
    {
        double protein = 0;

        if (unit.getGermanUnitName().equals(
                nutrition.getUnit().getGermanUnitName()))
        {
            protein = (amount * nutrition.getProtein());
        }
        else
        {
            if (unit.getGermanUnitName().equals(GermanUnitName.t))
            {
                protein = (nutrition.getProtein() / 1000000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.Kg))
            {
                protein = (nutrition.getProtein() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.mg))
            {
                protein = (nutrition.getProtein() / 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.ml))
            {
                protein = nutrition.getProtein() * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.l))
            {
                protein = (nutrition.getProtein() * 1000) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.EL))
            {
                protein = (nutrition.getProtein() * 15) * amount;
            }
            if (unit.getGermanUnitName().equals(GermanUnitName.TL))
            {
                protein = (nutrition.getProtein() * 5) * amount;
            }
        }

        return protein;
    }

}

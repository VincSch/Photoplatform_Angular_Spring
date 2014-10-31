/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.RecipeBook;
import de.htw.sdf.photoplatform.repository.RecipeBookDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * repository methods for recipe books.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 * 
 */
@Repository
@Transactional
public class RecipeBookDAOImpl extends GenericDAOImpl<RecipeBook> implements RecipeBookDAO
{

    /**
     * Recipe Book DAO constructor.
     */
    public RecipeBookDAOImpl()
    {
        super();
        setClazz(RecipeBook.class);
    }

    @Override
    public RecipeBook findByName(String name)
    {
        String queryString = "SELECT recipeBook FROM RecipeBook recipeBook "
                + "LEFT JOIN FETCH recipeBook.usedRecipes usedRecipes "
                + "LEFT JOIN FETCH usedRecipes.recipe recipe " + "WHERE recipeBook.name like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return (RecipeBook) query.getSingleResult();
    }

    @Override
    public List<RecipeBook> findByUserName(String name)
    {
        String queryString = "SELECT DISTINCT(recipeBook) FROM RecipeBook recipeBook "
                + "LEFT JOIN FETCH recipeBook.usedRecipes usedRecipes "
                + "LEFT JOIN FETCH usedRecipes.recipe recipe "
                + "WHERE recipeBook.createdBy like ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, name);
        return query.getResultList();
    }

}

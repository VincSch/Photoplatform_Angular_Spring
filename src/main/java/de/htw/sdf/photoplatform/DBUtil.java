/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.common.Constants;
import de.htw.sdf.photoplatform.persistence.models.user.UserBank;
import de.htw.sdf.photoplatform.persistence.models.user.UserProfile;
import de.htw.sdf.photoplatform.repository.*;
import de.htw.sdf.photoplatform.repository.UserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.NutritionFactManager;
import de.htw.sdf.photoplatform.manager.RecipeManager;
import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.manager.UsedIngredientManager;
import de.htw.sdf.photoplatform.persistence.models.Ingredient;
import de.htw.sdf.photoplatform.persistence.models.NutritionFact;
import de.htw.sdf.photoplatform.persistence.models.Recipe;
import de.htw.sdf.photoplatform.persistence.models.RecipeDifficulty;
import de.htw.sdf.photoplatform.persistence.models.user.Role;
import de.htw.sdf.photoplatform.persistence.models.Unit;
import de.htw.sdf.photoplatform.persistence.models.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.persistence.models.UsedIngredient;
import de.htw.sdf.photoplatform.persistence.models.user.User;
import de.htw.sdf.photoplatform.persistence.models.user.UserRole;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
@Service
public class DBUtil
{
    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected RoleDAO roleDAO;

    @Autowired
    protected UserRoleDAO userRoleDAO;

    @Autowired
    protected UserProfileDAO userProfileDAO;

    @Autowired
    protected UserBankDAO userBankDAO;


//    @Autowired
//    protected IngredientManager ingredientManager;
//
//    @Autowired
//    protected UnitManager unitManager;
//
//    @Autowired
//    protected RecipeManager recipeManager;
//
//    @Autowired
//    protected UsedIngredientManager usedIngredientManager;
//
//    @Autowired
//    protected NutritionFactDAO nutritionFactDAO;
//
//    @Autowired
//    protected NutritionFactManager nutritionManager;
//
//    @Autowired
//    protected RecipeDifficultyDAO difficultyDAO;



    /**
     * Insert test data.
     */
    public void insertTestData()
    {
        createRoles();
        createUser();

//        createUnits();
//        createIngredients();
//        createRecipes();
//        createRecipeIngredients();
//        createCaloriesForIngredients();
    }

    private void createRoles(){
        Role admin = new Role();
        admin.setName("ADMIN");
        roleDAO.create(admin);

        Role customer = new Role();
        customer.setName("CUSTOMER");
        roleDAO.create(customer);

        Role photographer = new Role();
        photographer.setName("PHOTOGRAPHER");
        roleDAO.create(photographer);
    }

    /**
     * Create user.
     */
    private void createUser()
    {
        Role admin = roleDAO.findOne(Constants.ROLE_ADMIN);
        createDefaultUser("Vincent","123","vincent@test.de",admin);

        Role customer = roleDAO.findOne(Constants.ROLE_CUSTOMER);
        createDefaultUser("Peter","123","peter@test.de",customer);

        Role photographer = roleDAO.findOne(Constants.ROLE_PHOTOGRAPHER);
        User sergej = createDefaultUser("Sergej","123","sergej@test.de", photographer);

        UserProfile sergejProfile = new UserProfile();
        sergejProfile.setAddress("Berlinerstraße 12,12207 Berlin");
        sergejProfile.setCompany("Burg");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        sergejProfile.setBirthday(df.format(new Date()));
        sergejProfile.setPhone("016568334");
        sergejProfile.setUser(sergej);
        userProfileDAO.create(sergejProfile);

        UserBank sergejBank = new UserBank();
        sergejBank.setUser(sergej);
        sergejBank.setReceiver("Sergej Meister");
        sergejBank.setIban("DE50344342342424");
        sergejBank.setBic("DEFGHKZ");
        userBankDAO.create(sergejBank);
    }

    private User createDefaultUser(String username, String password, String email,Role role) {
        User defaultUser = new User();
        defaultUser.setUserName(username);
        defaultUser.setPassword(password);
        defaultUser.setEnabled(true);
        defaultUser.setAccountNonLocked(true);
        defaultUser.setEmail(email);
        userDAO.create(defaultUser);

        UserRole userRole  = new UserRole();
        userRole.setRole(role);
        userRole.setUser(defaultUser);
        userRoleDAO.create(userRole);

        return defaultUser;
    }


//    /**
//     * Create units.
//     */
//    private void createUnits()
//    {
//        Unit el = new Unit();
//        el.setGermanUnitName(GermanUnitName.EL);
//        unitManager.create(el);
//
//        Unit g = new Unit();
//        g.setGermanUnitName(GermanUnitName.g);
//        unitManager.create(g);
//
//        Unit kg = new Unit();
//        kg.setGermanUnitName(GermanUnitName.Kg);
//        unitManager.create(kg);
//
//        Unit mg = new Unit();
//        mg.setGermanUnitName(GermanUnitName.mg);
//        unitManager.create(mg);
//
//        Unit t = new Unit();
//        t.setGermanUnitName(GermanUnitName.t);
//        unitManager.create(t);
//
//        Unit tl = new Unit();
//        tl.setGermanUnitName(GermanUnitName.TL);
//        unitManager.create(tl);
//
//        Unit l = new Unit();
//        l.setGermanUnitName(GermanUnitName.l);
//        unitManager.create(l);
//
//        Unit ml = new Unit();
//        ml.setGermanUnitName(GermanUnitName.ml);
//        unitManager.create(ml);
//    }

//    /**
//     * Create ingredients.
//     */
//    private void createIngredients()
//    {
//        Ingredient mehl = new Ingredient();
//        mehl.setName("Mehl");
//        ingredientManager.create(mehl);
//
//        Ingredient zucker = new Ingredient();
//        zucker.setName("Zucker");
//        ingredientManager.create(zucker);
//
//        Ingredient salz = new Ingredient();
//        salz.setName("Salz");
//        ingredientManager.create(salz);
//
//        Ingredient pfeffer = new Ingredient();
//        pfeffer.setName("Pfeffer");
//        ingredientManager.create(pfeffer);
//    }

//    /**
//     * Create recipes.
//     */
//    private void createRecipes()
//    {
//
//        /**
//         * create recipe difficulties
//         */
//        RecipeDifficulty easy = new RecipeDifficulty();
//        easy.setName("leicht");
//        difficultyDAO.create(easy);
//
//        RecipeDifficulty intermediate = new RecipeDifficulty();
//        intermediate.setName("mittel");
//        difficultyDAO.create(intermediate);
//
//        RecipeDifficulty advanced = new RecipeDifficulty();
//        advanced.setName("schwer");
//        difficultyDAO.create(advanced);
//
//        /**
//         * create recipes
//         */
//        Recipe eierkuchen = new Recipe();
//        eierkuchen.setName("Eierkuchen");
//        eierkuchen.setNotes("Weniger ist mehr!");
//        eierkuchen.setPreperation("Rühren wenden essen :)");
//        eierkuchen.setDifficulty(advanced);
//        eierkuchen.setCreatedBy("Vincent");
//        recipeManager.create(eierkuchen);
//
//        Recipe eintopf = new Recipe();
//        eintopf.setName("Eintopf");
//        eintopf.setCreatedBy("Vincent");
//        recipeManager.create(eintopf);
//
//        Recipe pizza = new Recipe();
//        pizza.setName("Pizza");
//        pizza.setCreatedBy("Vincent");
//        recipeManager.create(pizza);
//
//        Recipe rolade = new Recipe();
//        rolade.setName("Roladen");
//        rolade.setCreatedBy("Vincent");
//        recipeManager.create(rolade);
//
//    }

//    /**
//     * Create recipe ingredients.
//     */
//    private void createRecipeIngredients()
//    {
//        UsedIngredient mapping1 = new UsedIngredient();
//        mapping1.setAmount(2.0);
//        mapping1.setRecipe(recipeManager.findByName("Eierkuchen"));
//        mapping1.setIngredient(ingredientManager.findByName("Zucker"));
//        mapping1.setUnit(unitManager.findByName(GermanUnitName.EL));
//        recipeManager.addIngredient(mapping1);
//
//        UsedIngredient mapping5 = new UsedIngredient();
//        mapping5.setAmount(500.0);
//        mapping5.setRecipe(recipeManager.findByName("Eierkuchen"));
//        mapping5.setIngredient(ingredientManager.findByName("Mehl"));
//        mapping5.setUnit(unitManager.findByName(GermanUnitName.g));
//        recipeManager.addIngredient(mapping5);
//
//        UsedIngredient mapping2 = new UsedIngredient();
//        mapping2.setAmount(1.0);
//        mapping2.setRecipe(recipeManager.findByName("Pizza"));
//        mapping2.setIngredient(ingredientManager.findByName("Mehl"));
//        mapping2.setUnit(unitManager.findByName(GermanUnitName.Kg));
//        recipeManager.addIngredient(mapping2);
//
//        UsedIngredient mapping3 = new UsedIngredient();
//        mapping3.setAmount(10.0);
//        mapping3.setRecipe(recipeManager.findByName("Pizza"));
//        mapping3.setIngredient(ingredientManager.findByName("Salz"));
//        mapping3.setUnit(unitManager.findByName(GermanUnitName.mg));
//        recipeManager.addIngredient(mapping3);
//
//    }

//    /**
//     * Create Calories For Ingredients.
//     */
//    private void createCaloriesForIngredients()
//    {
//        NutritionFact mapping2 = new NutritionFact();
//        mapping2.setAmount(1.0);
//        mapping2.setUnit(unitManager.findByName(GermanUnitName.g));
//        mapping2.setIngredient(ingredientManager.findByName("Mehl"));
//        mapping2.setCalories(3.2);
//        mapping2.setFat(0.02);
//        mapping2.setProtein(0.1);
//        mapping2.setCarbohydrate(0.6);
//        nutritionManager.create(mapping2);
//    }

    /**
     * Clear tables.
     */
    public void clearTables()
    {
        userBankDAO.deleteAll();
        userProfileDAO.deleteAll();
        userRoleDAO.deleteAll();
        userDAO.deleteAll();
        roleDAO.deleteAll();

//        unitManager.deleteAll();
//        usedIngredientManager.deleteAll();
//        recipeManager.deleteAll();
//        nutritionFactDAO.deleteAll();
//        ingredientManager.deleteAll();
//        difficultyDAO.deleteAll();
    }
}

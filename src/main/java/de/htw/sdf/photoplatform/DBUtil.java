package de.htw.sdf.photoplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.NutritionFactManager;
import de.htw.sdf.photoplatform.manager.RecipeManager;
import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.manager.UsedIngredientManager;
import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.persistence.NutritionFact;
import de.htw.sdf.photoplatform.persistence.Recipe;
import de.htw.sdf.photoplatform.persistence.RecipeDifficulty;
import de.htw.sdf.photoplatform.persistence.Role;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.UsedIngredient;
import de.htw.sdf.photoplatform.persistence.User;
import de.htw.sdf.photoplatform.persistence.UserRole;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.repository.NutritionFactDAO;
import de.htw.sdf.photoplatform.repository.RecipeDifficultyDAO;
import de.htw.sdf.photoplatform.repository.RoleDAO;
import de.htw.sdf.photoplatform.repository.UserDAO;
import de.htw.sdf.photoplatform.repository.UserRoleDAO;

@Service
public class DBUtil {

	@Autowired
	protected IngredientManager ingredientManager;

	@Autowired
	protected UnitManager unitManager;

	@Autowired
	protected RecipeManager recipeManager;

	@Autowired
	protected UsedIngredientManager usedIngredientManager;
	
	@Autowired
	protected NutritionFactDAO nutritionFactDAO;
	
	@Autowired
	protected NutritionFactManager nutritionManager;

	@Autowired
	protected RecipeDifficultyDAO difficultyDAO;

	@Autowired
	protected UserDAO userDAO;

	@Autowired
	protected RoleDAO roleDAO;

	@Autowired
	protected UserRoleDAO userRoleDAO;
	
	public void insertDestData() {
		createUnits();
		createIngredients();
		createRecipes();
		createRecipeIngredients();
		createUser();
		createCaloriesForIngredients();
	}

	private void createUser() {
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleDAO.create(userRole);
		roleDAO.create(adminRole);

		User vincent = new User();
		vincent.setUserName("Vincent");
		vincent.setPassword("123");
		vincent.setEnabled(true);
		vincent.setAccountNonLocked(true);
		vincent.setBirthday("29.10.1987");
		vincent.setAbout("Mal sehen");
		vincent.setCity("Berlin");
		vincent.setEmail("test@test.de");
		userDAO.create(vincent);

		User peter = new User();
		peter.setUserName("Peter");
		peter.setPassword("123");
		peter.setEnabled(true);
		peter.setAccountNonLocked(true);
		userDAO.create(peter);

		UserRole mapping1 = new UserRole();
		mapping1.setRole(userRole);
		mapping1.setUser(vincent);
		userRoleDAO.create(mapping1);

		UserRole mapping3 = new UserRole();
		mapping3.setRole(adminRole);
		mapping3.setUser(vincent);
		userRoleDAO.create(mapping3);

		UserRole mapping2 = new UserRole();
		mapping2.setRole(userRole);
		mapping2.setUser(peter);
		userRoleDAO.create(mapping2);

	}

	private void createUnits() {
		Unit el = new Unit();
		el.setGermanUnitName(GermanUnitName.EL);
		unitManager.create(el);

		Unit g = new Unit();
		g.setGermanUnitName(GermanUnitName.g);
		unitManager.create(g);

		Unit kg = new Unit();
		kg.setGermanUnitName(GermanUnitName.Kg);
		unitManager.create(kg);

		Unit mg = new Unit();
		mg.setGermanUnitName(GermanUnitName.mg);
		unitManager.create(mg);

		Unit t = new Unit();
		t.setGermanUnitName(GermanUnitName.t);
		unitManager.create(t);

		Unit tl = new Unit();
		tl.setGermanUnitName(GermanUnitName.TL);
		unitManager.create(tl);

		Unit l = new Unit();
		l.setGermanUnitName(GermanUnitName.l);
		unitManager.create(l);

		Unit ml = new Unit();
		ml.setGermanUnitName(GermanUnitName.ml);
		unitManager.create(ml);
	}

	private void createIngredients() {

		Ingredient mehl = new Ingredient();
		mehl.setName("Mehl");
		ingredientManager.create(mehl);

		Ingredient zucker = new Ingredient();
		zucker.setName("Zucker");
		ingredientManager.create(zucker);

		Ingredient salz = new Ingredient();
		salz.setName("Salz");
		ingredientManager.create(salz);

		Ingredient pfeffer = new Ingredient();
		pfeffer.setName("Pfeffer");
		ingredientManager.create(pfeffer);

	}

	private void createRecipes() {

		/**
		 * create recipe difficulties
		 */
		RecipeDifficulty easy = new RecipeDifficulty();
		easy.setName("leicht");
		difficultyDAO.create(easy);

		RecipeDifficulty intermediate = new RecipeDifficulty();
		intermediate.setName("mittel");
		difficultyDAO.create(intermediate);

		RecipeDifficulty advanced = new RecipeDifficulty();
		advanced.setName("schwer");
		difficultyDAO.create(advanced);

		/**
		 * create recipes
		 */
		Recipe eierkuchen = new Recipe();
		eierkuchen.setName("Eierkuchen");
		eierkuchen.setNotes("Weniger ist mehr!");
		eierkuchen.setPreperation("Rühren wenden essen :)");
		eierkuchen.setDifficulty(advanced);
		eierkuchen.setCreatedBy("Vincent");
		recipeManager.create(eierkuchen);

		Recipe eintopf = new Recipe();
		eintopf.setName("Eintopf");
		eintopf.setCreatedBy("Vincent");
		recipeManager.create(eintopf);

		Recipe pizza = new Recipe();
		pizza.setName("Pizza");
		pizza.setCreatedBy("Vincent");
		recipeManager.create(pizza);

		Recipe rolade = new Recipe();
		rolade.setName("Roladen");
		rolade.setCreatedBy("Vincent");
		recipeManager.create(rolade);

	}

	private void createRecipeIngredients() {
		UsedIngredient mapping1 = new UsedIngredient();
		mapping1.setAmount(2.0);
		mapping1.setRecipe(recipeManager.findByName("Eierkuchen"));
		mapping1.setIngredient(ingredientManager.findByName("Zucker"));
		mapping1.setUnit(unitManager.findByName(GermanUnitName.EL));
		recipeManager.addIngredient(mapping1);

		UsedIngredient mapping5 = new UsedIngredient();
		mapping5.setAmount(500.0);
		mapping5.setRecipe(recipeManager.findByName("Eierkuchen"));
		mapping5.setIngredient(ingredientManager.findByName("Mehl"));
		mapping5.setUnit(unitManager.findByName(GermanUnitName.g));
		recipeManager.addIngredient(mapping5);

		UsedIngredient mapping2 = new UsedIngredient();
		mapping2.setAmount(1.0);
		mapping2.setRecipe(recipeManager.findByName("Pizza"));
		mapping2.setIngredient(ingredientManager.findByName("Mehl"));
		mapping2.setUnit(unitManager.findByName(GermanUnitName.Kg));
		recipeManager.addIngredient(mapping2);

		UsedIngredient mapping3 = new UsedIngredient();
		mapping3.setAmount(10.0);
		mapping3.setRecipe(recipeManager.findByName("Pizza"));
		mapping3.setIngredient(ingredientManager.findByName("Salz"));
		mapping3.setUnit(unitManager.findByName(GermanUnitName.mg));
		recipeManager.addIngredient(mapping3);

	}
	
	private void createCaloriesForIngredients() {
		NutritionFact mapping2 = new NutritionFact();
		mapping2.setAmount(1.0);
		mapping2.setUnit(unitManager.findByName(GermanUnitName.g));
		mapping2.setIngredient(ingredientManager.findByName("Mehl"));
		mapping2.setCalories(3.2);
		mapping2.setFat(0.02);
		mapping2.setProtein(0.1);
		mapping2.setCarbohydrate(0.6);
		nutritionManager.create(mapping2);
	}

	public void clearTables() {
		usedIngredientManager.deleteAll();
		recipeManager.deleteAll();
		nutritionFactDAO.deleteAll();;
		ingredientManager.deleteAll();
		unitManager.deleteAll();
		userRoleDAO.deleteAll();
		userDAO.deleteAll();
		roleDAO.deleteAll();
		difficultyDAO.deleteAll();	
	}
}

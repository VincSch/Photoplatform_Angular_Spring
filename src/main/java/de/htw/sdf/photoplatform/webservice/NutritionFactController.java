package de.htw.sdf.photoplatform.webservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.htw.sdf.photoplatform.manager.IngredientManager;
import de.htw.sdf.photoplatform.manager.NutritionFactManager;
import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.persistence.Ingredient;
import de.htw.sdf.photoplatform.persistence.NutritionFact;
import de.htw.sdf.photoplatform.persistence.UsedIngredient;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.webservice.common.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.common.Endpoints;

@Controller
public class NutritionFactController extends BaseAPIController {

	@Autowired
	NutritionFactManager nutritionFactManager;

	@Autowired
	UnitManager unitManager;

	@Autowired
	IngredientManager ingredientManager;

	@RequestMapping(value = Endpoints.NUTRITION_BY_INGREDIENT, method = RequestMethod.GET)
	@ResponseBody
	public NutritionFact recipeBookByName(@PathVariable Long id)
			throws Exception {
		NutritionFact fact = nutritionFactManager.findByIngredientId(id);
		return fact;
	}

	@RequestMapping(value = Endpoints.CALORIES_FOR_USEDINGREDIENTS, method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Double> calroiesForUsedIngredients(
			@RequestBody List<UsedIngredient> usedIngredients) throws Exception {
		return nutritionFactManager.calculateCalories(usedIngredients);
	}

	@RequestMapping(value = Endpoints.NUTRITIONFACT_CREATE, method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public NutritionFact createRecipe(@RequestBody NutritionFact fact) {
		fact.setUnit(unitManager.findByName(GermanUnitName.g));
		fact.setIngredient(ingredientManager.findByName(fact.getIngredient().getName()));
		nutritionFactManager.create(fact);
		return fact;
	}
	
	@RequestMapping(value = Endpoints.NUTRITIONFACT_UPDATE, method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public boolean updateRecipe(
			@RequestBody NutritionFact nutrition) {
		nutritionFactManager.update(nutrition);
		return true;
	}
}

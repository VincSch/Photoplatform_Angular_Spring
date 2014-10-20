package de.htw.sdf.photoplatform.webservice;

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

import de.htw.sdf.photoplatform.manager.RecipeBookManager;
import de.htw.sdf.photoplatform.persistence.RecipeBook;
import de.htw.sdf.photoplatform.persistence.UsedRecipe;
import de.htw.sdf.photoplatform.webservice.common.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.common.Endpoints;

@Controller
public class RecipeBookController extends BaseAPIController {

	@Autowired
	RecipeBookManager recipeBookManager;

	@RequestMapping(value = Endpoints.RECIPEBOOK_BY_NAME, method = RequestMethod.GET)
	@ResponseBody
	public RecipeBook recipeBookByName(@PathVariable String name)
			throws Exception {
		RecipeBook recipeBook = recipeBookManager.findByName(name);
		return recipeBook;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_BY_USERNAME, method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeBook> recipeByUserName(@PathVariable String name) {
		return recipeBookManager.findByUserName(name);
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_ALL, method = RequestMethod.GET)
	@ResponseBody
	public List<RecipeBook> retrieveAllRecipeBooks() throws Exception {
		List<RecipeBook> recipeBookList = recipeBookManager.findAll();
		return recipeBookList;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_DELETE_BY_NAME, method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteRecipeBookByName(@PathVariable String name) {
		RecipeBook recipeBookToDelete = recipeBookManager.findByName(name);
		recipeBookManager.delete(recipeBookToDelete);
		return true;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_CREATE, method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public RecipeBook createRecipeBook(@RequestBody RecipeBook recipeBook) {
		recipeBookManager.create(recipeBook);
		return recipeBook;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_UPDATE, method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public boolean updateRecipe(@PathVariable long id,
			@RequestBody RecipeBook recipeBook) {
		recipeBookManager.update(recipeBook);
		return true;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_REMOVE_RECIPE, method = RequestMethod.POST)
	@ResponseBody
	public boolean removeRecipe(@RequestBody UsedRecipe usedRecipe) {
		recipeBookManager.deleteRecipe(usedRecipe);
		return true;
	}

	@RequestMapping(value = Endpoints.RECIPEBOOK_ADD_RECIPE, method = RequestMethod.POST)
	@ResponseBody
	public boolean addRecipe(@RequestBody UsedRecipe usedRecipe) {
		recipeBookManager.addRecipe(usedRecipe);
		return true;
	}
}

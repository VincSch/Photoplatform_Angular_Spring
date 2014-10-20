angular.module('recipebook').factory('RecipeService', ['$http',
function($http) {

	var urlBase = '/api/recipe';
	var recipeService = {};

	recipeService.getAllRecipes = function() {
		return $http.get(urlBase + '/all');
	};

	recipeService.getRecipesByUserName = function(name) {
		return $http.get(urlBase + '/byusername/' + name);
	};

	recipeService.getRecipeByName = function(name) {
		return $http.get(urlBase + '/byname/' + name);
	};

	recipeService.getRecipeById = function(id) {
		return $http.get(urlBase + '/byid/' + id);
	};

	recipeService.updateRecipe = function(recipe) {
		return $http.put(urlBase + '/update/' + recipe.id, recipe);
	};

	recipeService.createRecipe = function(recipe) {
		return $http.post(urlBase + '/create/', recipe);
	};

	recipeService.getDifficulties = function(difficulties) {
		return $http.get(urlBase + '/difficulty/all');
	};

	recipeService.removeUsedIngredient = function(usedIngredient) {
		return $http.post(urlBase + '/remove/ingredient', usedIngredient);
	};

	recipeService.addUsedIngredient = function(recipe, ingredient, amount, unit) {

		var paramData = null;
		paramData = {
			webRecipe : recipe,
			webIngredient : ingredient,
			webAmount : amount,
			webUnit : unit
		};
		return $http.post(urlBase + '/add/ingredient', angular.toJson(paramData));
	};

	return recipeService;
}]);

angular.module('recipebook').factory('IngredientService', ['$http',
function($http) {

	var urlBase = '/api/ingredient';
	var ingredientService = {};

	ingredientService.getIngredientByName = function(name) {
		return $http.get(urlBase + '/byname/' + name);
	};

	ingredientService.createIngredient = function(ingredient) {
		return $http.post(urlBase + '/create/', ingredient);
	};

	ingredientService.getAllIngredients = function() {
		return $http.get(urlBase + '/all');
	};

	ingredientService.updateIngredient = function(ingredient) {
		return $http.put(urlBase + '/update/' + ingredient.id, ingredient);
	};

	return ingredientService;
}]);

angular.module('recipebook').factory('UnitService', ['$http',
function($http) {

	var urlBase = '/api/unit';
	var unitService = {};

	unitService.getAllUnits = function() {
		return $http.get(urlBase + '/all');
	};

	return unitService;
}]);

angular.module('recipebook').factory('RecipeBookService', ['$http',
function($http) {

	var urlBase = '/api/recipebook';
	var recipeBookService = {};

	recipeBookService.getAllRecipeBooks = function() {
		return $http.get(urlBase + '/all');
	};

	recipeBookService.getRecipeBookByName = function(name) {
		return $http.get(urlBase + '/byname/' + name);
	};

	recipeBookService.getRecipeBooksByUserName = function(name) {
		return $http.get(urlBase + '/byusername/' + name);
	};

	recipeBookService.createRecipeBook = function(recipeBook) {
		return $http.post(urlBase + '/create/', recipeBook);
	};

	recipeBookService.addUsedRecipe = function(usedRecipe) {
		return $http.post(urlBase + '/add/recipe', usedRecipe);
	};

	recipeBookService.removeUsedRecipe = function(usedRecipe) {
		return $http.post(urlBase + '/remove/recipe', usedRecipe);
	};

	recipeBookService.updateRecipeBook = function(recipeBook) {
		return $http.put(urlBase + '/update/' + recipeBook.id, recipeBook);
	};

	return recipeBookService;
}]);

angular.module('recipebook').factory('UserService', ['$http',
function($http) {

	var urlBase = '/api/user';
	var userService = {};

	userService.login = function(name, pw) {
		paramData = {
			username : name,
			password : pw
		};

		return $http.post(urlBase + '/login', paramData);
	};

	userService.register = function(user) {
		return $http.post(urlBase + '/register', user);
	};

	userService.update = function(user) {
		return $http.post(urlBase + '/update', user);
	};

	userService.getUserByName = function(name) {
		return $http.get(urlBase + '/byname/' + name);
	};

	userService.getRecipeAmount = function(name) {
		return $http.get(urlBase + '/recipes/amount/' + name);
	};

	userService.getRecipeBookAmount = function(name) {
		return $http.get(urlBase + '/recipebooks/amount/' + name);
	};

	return userService;
}]);

angular.module('recipebook').factory('NutritionService', ['$http',
function($http) {

	var urlBase = '/api/nutritionfact';
	var nutritionService = {};

	nutritionService.getNutritionsForIngredients = function(usedIngredients) {
		return $http.post(urlBase + '/caloriesforusedingredients/', usedIngredients);
	};

	nutritionService.createNutritionFact = function(nutrition) {
		return $http.post(urlBase + '/create/', nutrition);
	};

	nutritionService.getNutritionByIngredientId = function(id) {
		return $http.get(urlBase + '/byingredient/' + id);
	};

	nutritionService.update = function(nutrition) {
		return $http.put(urlBase + '/update', nutrition);
	};

	return nutritionService;
}]);


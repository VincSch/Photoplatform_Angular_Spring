recipeBookControllers.controller('RecipeListCtrl', ['$scope', 'RecipeService',
function($scope, RecipeService) {	
	RecipeService.getAllRecipes().success(function(recipe) {
		$scope.recipes = recipe;
	}).error(function(error) {
		$scope.status = 'Unable to retrieve recipe data ' + error.message;
	});	
}]);

recipeBookControllers.controller('RecipeViewCtrl', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$cookieStore', 'RecipeService', 'NutritionService', '$route',
function($scope, $rootScope, $routeParams, $location, $http, $cookieStore, RecipeService, NutritionService, $route) {
	RecipeService.getRecipeById($routeParams.id).success(function(recipe) {
		
		NutritionService.getNutritionsForIngredients(recipe.usedIngredients).success(function(nutritions){
			$scope.nutritions = nutritions;
		});
		
		$scope.recipe = recipe;
	}).error(function(error) {
		$rootScope.error = "Rezept konnte nicht gefunden werden!";
	});
}]);

recipeBookControllers.controller('RecipeDetailCtrl', ['$scope', '$routeParams', '$location', '$route', '$rootScope', 'RecipeService', 'UnitService', 'IngredientService',
function($scope, $routeParams, $location, $route, $rootScope, RecipeService, UnitService, IngredientService) {

	RecipeService.getRecipeByName($routeParams.recipeName).success(function(recipe) {
		$scope.recipe = recipe;
		$scope.difficulty = recipe.difficulty;
		$scope.servesAmount = recipe.serves;
	}).error(function(error) {
		$scope.status = 'Unable to retrieve recipe data ' + error.message;
	});

	RecipeService.getDifficulties().success(function(difficulties) {
		$scope.difficulties = difficulties;
	}).error(function(error) {
		$scope.status = 'Unable to retrieve recipe data ' + error.message;
	});

	$scope.setNewDifficulty = function(difficulty) {
		$scope.recipe.difficulty = difficulty;
	};

	$scope.serves = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

	$scope.setServesAmount = function(servesAmount) {
		$scope.recipe.serves = servesAmount;
	};

	$scope.removeIngredient = function(usedIngredient) {
		RecipeService.removeUsedIngredient(usedIngredient).success(function(usedIngredient) {
			info = "Zutat wurde entfernt";
		}).error(function(error) {
			info = 'Unable to retrieve recipe data ' + error.message;
		});

		$route.reload();
	};

	IngredientService.getAllIngredients().success(function(ingredients) {
		$scope.ingredients = ingredients;
	}).error(function(error) {
		$scope.status = 'Unable to ingredients data ' + error.message;
	});

	UnitService.getAllUnits().success(function(units) {
		$scope.units = units;
	}).error(function(error) {
		$scope.status = 'Unable to unit data ' + error.message;
	});

	$scope.addIngredient = function(recipe, ingredient, amount, unit) {
		RecipeService.addUsedIngredient(recipe, ingredient, amount, unit).success(function(recipe, ingredient, amount, unit) {
			info = "Zutat wurde entfernt";
		}).error(function(error) {
			info = 'Unable to retrieve recipe data ' + error.message;
		});

		$route.reload();
	};

	$scope.updateRecipe = function(recipe) {
		recipe.updatedBy = $rootScope.user.username;
		RecipeService.updateRecipe(recipe).success(function(recipe) {
		}).error(function(error) {
			$scope.status = 'Unable to retrieve recipe data ' + error.message;
		});

		if ($rootScope.cmurl == undefined)
			$location.path("/recipe");
		else
			$location.path($rootScope.cmurl);
		delete $rootScope.cmurl;
	};

}]);

recipeBookControllers.controller('RecipeCreateCtrl', ['$scope', '$routeParams', '$location', '$route', '$rootScope', 'RecipeService', 'UnitService', 'IngredientService',
function($scope, $routeParams, $location, $route, $rootScope, RecipeService, UnitService, IngredientService) {
	var usedIngredients = null;
	$scope.recipe = createEmptyRecipe();

	RecipeService.getDifficulties().success(function(difficulties) {
		$scope.difficulties = difficulties;
	}).error(function(error) {
		$scope.status = 'Unable to retrieve recipe data ' + error.message;
	});

	$scope.setNewDifficulty = function(difficulty) {
		$scope.recipe.difficulty = difficulty;
	};

	$scope.serves = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

	$scope.setServesAmount = function(servesAmount) {
		$scope.recipe.serves = servesAmount;
	};

	IngredientService.getAllIngredients().success(function(ingredients) {
		$scope.ingredients = ingredients;
	}).error(function(error) {
		$scope.status = 'Unable to ingredients data ' + error.message;
	});

	UnitService.getAllUnits().success(function(units) {
		$scope.units = units;
	}).error(function(error) {
		$scope.status = 'Unable to unit data ' + error.message;
	});

	$scope.removeIngredient = function(usedIngredient) {
		$scope.recipe.usedIngredients.pop(usedIngredient);
	};

	$scope.addIngredient = function(recipe, ingredient, amount, unit) {
		var usedIngredient = createUsedIngredient(recipe, ingredient, amount, unit);
		$scope.recipe.usedIngredients.push(usedIngredient);
	};

	$scope.createRecipe = function(recipe) {
		$scope.usedIngredientsToCreate = recipe.usedIngredients;
		recipe.usedIngredients = null;
		recipe.createdBy = $rootScope.user.username;
		RecipeService.createRecipe(recipe).success(function(recipe) {
			createUsedIngredients(recipe);
		}).error(function(error) {
		});
	};

	function createUsedIngredients(recipe) {

		for (var i = 0; i < $scope.usedIngredientsToCreate.length; ++i) {
			usedIngredient = $scope.usedIngredientsToCreate[i];
			RecipeService.addUsedIngredient(recipe, usedIngredient.ingredient, usedIngredient.amount, usedIngredient.unit).success(function() {
			}).error(function(error) {
			});
		}

		if ($rootScope.cmurl == undefined)
			$location.path("/recipes");
		else
			$location.path($rootScope.cmurl);
		delete $rootScope.cmurl;
	}

}]);

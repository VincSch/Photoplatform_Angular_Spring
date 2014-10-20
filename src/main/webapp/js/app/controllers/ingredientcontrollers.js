recipeBookControllers.controller('IngredientListCtrl', ['$scope', '$routeParams', '$location', '$route', 'IngredientService',
function($scope, $routeParams, $location, $route, IngredientService) {

	IngredientService.getAllIngredients().success(function(ingredients) {
		$scope.ingredients = ingredients;
	}).error(function(error) {
	});

}]);

recipeBookControllers.controller('IngredientCreateCtrl', ['$scope', '$routeParams', '$location', '$route', 'IngredientService', 'NutritionService', 'UnitService',
function($scope, $routeParams, $location, $route, IngredientService, NutritionService, UnitService) {

	$scope.createIngredient = function(ingredient, nutrition) {
		var ingredientToBeStored = ingredient;
		IngredientService.createIngredient(ingredient).success(function(ingredient) {
			var nutritionFact = createNutritionFact(1.0, ingredientToBeStored, nutrition.calories, nutrition.protein, nutrition.fat, nutrition.carbohydrate);
			createNutrition(nutritionFact);
		}).error(function(error) {
		});

		$location.path("/ingredients");
	};

	function createNutrition(nutritionFact) {
		NutritionService.createNutritionFact(nutritionFact).success(function() {
		}).error(function(error) {

		});
	}

}]);

recipeBookControllers.controller('IngredientDetailCtrl', ['$scope', '$routeParams', '$location', '$route', 'IngredientService', 'NutritionService',
function($scope, $routeParams, $location, $route, IngredientService, NutritionService) {

	IngredientService.getIngredientByName($routeParams.ingredientName).success(function(ingredient) {
		$scope.ingredient = ingredient;
		getNutritionInfo(ingredient);
	}).error(function(error) {
	});

	function getNutritionInfo(ingredient) {
		NutritionService.getNutritionByIngredientId(ingredient.id).success(function(nutrition) {
			$scope.nutrition = nutrition;
		}).error(function(error) {
		});
	}

	function updateNutritionInfo(nutrition) {
		NutritionService.update(nutrition).success(function(nutrition) {
		}).error(function(error) {
		});
	}


	$scope.updateIngredient = function(ingredient, nutrition) {
		IngredientService.updateIngredient(ingredient).success(function() {
			updateNutritionInfo(nutrition);
		}).error(function(error) {
		});

		$location.path("/ingredients");
	};
}]);

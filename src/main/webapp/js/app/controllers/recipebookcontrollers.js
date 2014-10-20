recipeBookControllers.controller('RecipeBookListCtrl', ['$scope', 'RecipeBookService',
function($scope, RecipeBookService) {
	RecipeBookService.getAllRecipeBooks().success(function(recipeBooks) {
		$scope.recipeBooks = recipeBooks;
	}).error(function(error) {
	});
}]);

recipeBookControllers.controller('RecipeBookCreateCtrl', ['$scope', '$location', 'RecipeService', 'RecipeBookService', '$rootScope',
function($scope, $location, RecipeService, RecipeBookService, $rootScope) {
	$scope.recipeBook = createEmptyRecipeBook();

	RecipeService.getAllRecipes().success(function(recipe) {
		$scope.recipes = recipe;
	}).error(function(error) {
	});

	$scope.addRecipe = function(recipeBook, recipe) {
		var usedRecipe = createUsedRecipe(recipeBook, recipe);
		$scope.recipeBook.usedRecipes.push(usedRecipe);
	};

	$scope.removeRecipe = function(usedRecipe) {
		$scope.recipeBook.usedRecipes.pop(usedRecipe);
	};

	$scope.createRecipeBook = function(recipeBook) {
		$scope.usedRecipesToCreate = recipeBook.usedRecipes;
		recipeBook.usedRecipes = null;
		recipeBook.createdBy = $rootScope.user.username;
		RecipeBookService.createRecipeBook(recipeBook).success(function(recipeBook) {
			createUsedRecipes(recipeBook);
		}).error(function(error) {
		});
	};
	function createUsedRecipes(recipeBook) {

		for (var i = 0; i < $scope.usedRecipesToCreate.length; ++i) {
			var usedRecipe = $scope.usedRecipesToCreate[i];
			usedRecipe.recipeBook = recipeBook;
			RecipeBookService.addUsedRecipe(usedRecipe).success(function() {
			}).error(function(error) {
			});
		}

		if ($rootScope.cmurl == undefined)
			$location.path("/recipebooks");
		else
			$location.path($rootScope.cmurl);
		delete $rootScope.cmurl;
	}

}]);

recipeBookControllers.controller('RecipeBookDetailCtrl', ['$scope', '$routeParams', '$route', '$location', 'RecipeService', 'RecipeBookService', '$rootScope',
function($scope, $routeParams, $route, $location, RecipeService, RecipeBookService, $rootScope) {

	RecipeBookService.getRecipeBookByName($routeParams.recipeBookName).success(function(recipeBook) {
		$scope.recipeBook = recipeBook;
	}).error(function(error) {
		$scope.status = 'Unable to retrieve recipebook data ' + error.message;
	});

	RecipeService.getAllRecipes().success(function(recipe) {
		$scope.recipes = recipe;
	}).error(function(error) {
	});

	$scope.addRecipe = function(recipeBook, recipe) {
		var usedRecipe = createUsedRecipe(recipeBook, recipe);
		$scope.recipeBook.usedRecipes.push(usedRecipe);
	};

	$scope.removeRecipe = function(usedRecipe) {
		RecipeBookService.removeUsedRecipe(usedRecipe).success(function(usedRecipe) {
			info = "Rezept wurde entfernt";
		}).error(function(error) {
			info = 'Unable to retrieve recipe data ' + error.message;
		});

		$route.reload();
	};

	function createUsedRecipes(recipeBook) {

		for (var i = 0; i < recipeBook.usedRecipes.length; ++i) {
			var usedRecipe = recipeBook.usedRecipes[i];
			usedRecipe.recipeBook = recipeBook;
			RecipeBookService.addUsedRecipe(usedRecipe).success(function() {
			}).error(function(error) {
			});
		}
	}


	$scope.updateRecipeBook = function(recipeBook) {
		recipeBook.updatedBy = $rootScope.user.username;
		createUsedRecipes(recipeBook);
		recipeBook.usedRecipes = null;
		RecipeBookService.updateRecipeBook(recipeBook).success(function(recipeBook) {
		}).error(function(error) {
			$scope.status = 'Unable to retrieve recipebook data ' + error.message;
		});

		if ($rootScope.cmurl == undefined)
			$location.path("/recipebooks");
		else
			$location.path($rootScope.cmurl);
		delete $rootScope.cmurl;
	};

}]);

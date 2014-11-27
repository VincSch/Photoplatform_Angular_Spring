function createEmptyRecipe() {
    var recipe = new Object();
    recipe.id = null;
    recipe.name = null;
    recipe.difficulty = null;
    recipe.serves = null;
    recipe.preperationTime = null;
    recipe.preperation = null;
    recipe.notes = null;
    recipe.usedIngredients = new Array();
    return recipe;
};

function createUsedIngredient(recipe, ingredient, amount, unit) {
    var usedIngredient = new Object();
    usedIngredient.id = null;
    usedIngredient.recipe = recipe;
    usedIngredient.ingredient = ingredient;
    usedIngredient.amount = amount;
    usedIngredient.unit = unit;
    return usedIngredient;
};

function createEmptyRecipeBook() {
    var recipeBook = new Object();
    recipeBook.id = null;
    recipeBook.name = null;
    recipeBook.usedRecipes = new Array();
    recipeBook.type = null;
    return recipeBook;
};

function createUsedRecipe(recipeBook, recipe) {
    var usedRecipe = new Object();
    usedRecipe.id = null;
    usedRecipe.recipeBook = recipeBook;
    usedRecipe.recipe = recipe;
    return usedRecipe;
};

function createUser(name, password, email) {
    var user = new Object();
    user.id = null;
    user.username = name;
    user.password = password;
    user.email = email;
    user.accountNonLocked = true;
    user.enabled = true;
    user.birthday = null;
    user.city = null;
    user.about = null;
    user.secToken = null;
    user.userRoles = null;
    return user;
};

function createNutritionFact(amount, ingredient, calories, protein, fat, carbohydrate) {
    var nutrition = new Object();
    nutrition.id = null;
    nutrition.amount = amount;
    nutrition.ingredient = ingredient;
    nutrition.unit = null;
    nutrition.calories = calories;
    nutrition.protein = protein;
    nutrition.fat = fat;
    nutrition.carbohydrate = carbohydrate;
    return nutrition;
};
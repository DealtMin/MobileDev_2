package ru.mirea.belkinaed.recipeheaven.domain.repository;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;

public interface RecipeRepository {
    Recipe getRecipe(int id);
    Recipe[] getRecipes();
    Recipe[] getRecipesByWeatherType(String type);
    Recipe[] getRecipesByUser(int userId);
    boolean CreateRecipe(Recipe data);
    boolean UpdateRecipe(int id, Recipe data);
    boolean DeleteRecipe(int id);
}

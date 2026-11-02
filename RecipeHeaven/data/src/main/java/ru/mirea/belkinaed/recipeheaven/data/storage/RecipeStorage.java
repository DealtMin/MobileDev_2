package ru.mirea.belkinaed.recipeheaven.data.storage;

import java.util.List;

import ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe;

public interface RecipeStorage {
    Recipe getRecipeById(int id);
    List<Recipe> getAllRecipes();
    List<Recipe> getRecipesByWeatherType(String type);
    List<Recipe> getRecipesByUserId(int userId);
    boolean insertRecipe(Recipe recipe);
    boolean updateRecipe(Recipe recipe);
    boolean deleteRecipe(int id);
}

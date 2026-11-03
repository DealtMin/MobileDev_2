package ru.mirea.belkinaed.recipeheaven.presentation;

import java.util.List;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;

public class CombinedRecipeData {
    private final String weather;
    private final List<Recipe> recipes;

    public CombinedRecipeData(String weather, List<Recipe> recipes) {
        this.weather = weather;
        this.recipes = recipes;
    }

    public String getWeather() {
        return weather;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}

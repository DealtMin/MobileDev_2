package ru.mirea.belkinaed.recipeheaven.data.repository;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class RecipeRepositoryImpl implements RecipeRepository {
    private Recipe fake = new Recipe(0, "name","type","steps");
    private Recipe[] fakeArr = new Recipe[]{fake, fake, fake};
    @Override
    public Recipe getRecipe(int id) {
        return fake;
    }

    @Override
    public Recipe[] getRecipes() {
        return fakeArr;
    }

    @Override
    public Recipe[] getRecipesByWeatherType(String type) {
        return fakeArr;
    }

    @Override
    public Recipe[] getRecipesByUser(int userId) {
        return fakeArr;
    }

    @Override
    public boolean CreateRecipe(Recipe data) {
        return true;
    }

    @Override
    public boolean UpdateRecipe(int id, Recipe data) {
        return true;
    }

    @Override
    public boolean DeleteRecipe(int id) {
        return true;
    }
}

package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class ChangeRecipe {
    private RecipeRepository recipeRepository;

    public ChangeRecipe(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean execute(int id, Recipe data){
        return recipeRepository.UpdateRecipe(id,data);
    }
}

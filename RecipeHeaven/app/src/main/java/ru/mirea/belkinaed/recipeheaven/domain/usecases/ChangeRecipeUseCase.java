package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class ChangeRecipeUseCase {
    private RecipeRepository recipeRepository;

    public ChangeRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean execute(int id, Recipe data){
        return recipeRepository.UpdateRecipe(id,data);
    }
}

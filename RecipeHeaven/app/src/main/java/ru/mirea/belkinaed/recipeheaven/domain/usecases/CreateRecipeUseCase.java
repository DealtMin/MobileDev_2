package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class CreateRecipeUseCase {
    private RecipeRepository recipeRepository;

    public CreateRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean execute(Recipe data){
        return recipeRepository.CreateRecipe(data);
    }
}

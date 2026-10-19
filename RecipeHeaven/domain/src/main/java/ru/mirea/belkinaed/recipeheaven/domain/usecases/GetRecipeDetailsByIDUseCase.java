package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class GetRecipeDetailsByIDUseCase {
    private RecipeRepository recipeRepository;

    public GetRecipeDetailsByIDUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe execute(int id){
        return recipeRepository.getRecipe(id);
    }
}

package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class GetRecipesByUserUseCase {
    private RecipeRepository recipeRepository;

    public GetRecipesByUserUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe[] execute(int id){
        return recipeRepository.getRecipesByUser(id);
    }
}

package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class GetRecipesUseCaseUseCase {
    private RecipeRepository recipeRepository;

    public GetRecipesUseCaseUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe[] execute(){
        return recipeRepository.getRecipes();
    }
}

package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class DeleteRecipeUseCase {
    private RecipeRepository recipeRepository;

    public DeleteRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean execute(int id){
        return recipeRepository.DeleteRecipe(id);
    }
}

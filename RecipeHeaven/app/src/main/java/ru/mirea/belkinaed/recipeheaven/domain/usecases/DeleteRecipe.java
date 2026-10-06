package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class DeleteRecipe {
    private RecipeRepository recipeRepository;

    public DeleteRecipe(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean execute(int id){
        return recipeRepository.DeleteRecipe(id);
    }
}

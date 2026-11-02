package ru.mirea.belkinaed.recipeheaven.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.belkinaed.recipeheaven.data.storage.RecipeStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.database.RecipeDao;
import ru.mirea.belkinaed.recipeheaven.data.storage.database.RecipeDbStorage;

public class RecipeRepositoryImpl implements ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository {

    private final RecipeStorage recipeStorage;

    public RecipeRepositoryImpl(Context context) {
        this.recipeStorage = new RecipeDbStorage(context);
    }

    public RecipeRepositoryImpl(RecipeDao recipeDao) {
        this.recipeStorage = new RecipeDbStorage(recipeDao);
    }

    public RecipeRepositoryImpl(RecipeStorage recipeStorage) {
        this.recipeStorage = recipeStorage;
    }

    @Override
    public ru.mirea.belkinaed.recipeheaven.domain.models.Recipe getRecipe(int id) {
        if (recipeStorage == null) return null;
        ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe dataRecipe = recipeStorage.getRecipeById(id);
        return mapToDomain(dataRecipe);
    }

    @Override
    public ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[] getRecipes() {
        if (recipeStorage == null) return new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[0];
        List<ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe> dataRecipes = recipeStorage.getAllRecipes();
        return mapToDomainArray(dataRecipes);
    }

    @Override
    public ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[] getRecipesByWeatherType(String type) {
        if (recipeStorage == null) return new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[0];
        List<ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe> dataRecipes = recipeStorage.getRecipesByWeatherType(type);
        return mapToDomainArray(dataRecipes);
    }

    @Override
    public ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[] getRecipesByUser(int userId) {
        if (recipeStorage == null) return new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[0];
        List<ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe> dataRecipes = recipeStorage.getRecipesByUserId(userId);
        return mapToDomainArray(dataRecipes);
    }

    @Override
    public boolean CreateRecipe(ru.mirea.belkinaed.recipeheaven.domain.models.Recipe data) {
        if (recipeStorage == null || data == null) return false;
        try {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe dataRecipe = mapToData(data);
            return recipeStorage.insertRecipe(dataRecipe);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateRecipe(int id, ru.mirea.belkinaed.recipeheaven.domain.models.Recipe data) {
        if (recipeStorage == null || data == null) return false;
        try {
            ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe dataRecipe =
                    new ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe(
                            id, data.getName(), data.getType(), data.getSteps()
                    );
            return recipeStorage.updateRecipe(dataRecipe);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteRecipe(int id) {
        if (recipeStorage == null) return false;
        try {
            return recipeStorage.deleteRecipe(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ru.mirea.belkinaed.recipeheaven.domain.models.Recipe mapToDomain(
            ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe dataRecipe
    ) {
        if (dataRecipe == null) return null;
        return new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe(
                dataRecipe.getId(),
                dataRecipe.getName(),
                dataRecipe.getType(),
                dataRecipe.getSteps()
        );
    }

    private ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[] mapToDomainArray(
            List<ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe> dataRecipes
    ) {
        if (dataRecipes == null) return new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[0];
        List<ru.mirea.belkinaed.recipeheaven.domain.models.Recipe> domainList = new ArrayList<>();
        for (ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe dataRecipe : dataRecipes) {
            domainList.add(mapToDomain(dataRecipe));
        }
        return domainList.toArray(new ru.mirea.belkinaed.recipeheaven.domain.models.Recipe[0]);
    }

    private ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe mapToData(
            ru.mirea.belkinaed.recipeheaven.domain.models.Recipe domainRecipe
    ) {
        if (domainRecipe == null) return null;
        return new ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe(
                domainRecipe.getId(),
                domainRecipe.getName(),
                domainRecipe.getType(),
                domainRecipe.getSteps()
        );
    }
}

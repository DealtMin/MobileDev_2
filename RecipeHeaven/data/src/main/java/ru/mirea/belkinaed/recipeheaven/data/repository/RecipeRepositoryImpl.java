package ru.mirea.belkinaed.recipeheaven.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.belkinaed.recipeheaven.data.storage.database.AppDatabase;
import ru.mirea.belkinaed.recipeheaven.data.storage.database.RecipeDao;
import ru.mirea.belkinaed.recipeheaven.data.storage.database.RecipeEntity;
import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;

public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeDao recipeDao;

    public RecipeRepositoryImpl(Context context) {
        this.recipeDao = AppDatabase.getInstance(context).recipeDao();
    }

    public RecipeRepositoryImpl(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipeDao == null) return null;
        RecipeEntity entity = recipeDao.getRecipeById(id);
        return mapToDomain(entity);
    }

    @Override
    public Recipe[] getRecipes() {
        if (recipeDao == null) return new Recipe[0];
        List<RecipeEntity> entities = recipeDao.getAllRecipes();
        return mapToDomainList(entities);
    }

    @Override
    public Recipe[] getRecipesByWeatherType(String type) {
        if (recipeDao == null) return new Recipe[0];
        List<RecipeEntity> entities = recipeDao.getRecipesByWeatherType(type);
        return mapToDomainList(entities);
    }

    @Override
    public Recipe[] getRecipesByUser(int userId) {
        if (recipeDao == null) return new Recipe[0];
        List<RecipeEntity> entities = recipeDao.getRecipesByUserId(userId);
        return mapToDomainList(entities);
    }

    @Override
    public boolean CreateRecipe(Recipe data) {
        if (recipeDao == null || data == null) return false;
        try {
            RecipeEntity entity = mapToEntity(data);
            recipeDao.insertRecipe(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean UpdateRecipe(int id, Recipe data) {
        if (recipeDao == null || data == null) return false;
        try {
            RecipeEntity entity = new RecipeEntity(id, data.getName(), data.getType(), data.getSteps(), 0);
            recipeDao.updateRecipe(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteRecipe(int id) {
        if (recipeDao == null) return false;
        try {
            recipeDao.deleteRecipeById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Recipe mapToDomain(RecipeEntity entity) {
        if (entity == null) return null;
        return new Recipe(entity.getId(), entity.getName(), entity.getWeatherType(), entity.getSteps());
    }

    private Recipe[] mapToDomainList(List<RecipeEntity> entities) {
        if (entities == null) return new Recipe[0];
        List<Recipe> recipes = new ArrayList<>();
        for (RecipeEntity entity : entities) {
            recipes.add(mapToDomain(entity));
        }
        return recipes.toArray(new Recipe[0]);
    }

    private RecipeEntity mapToEntity(Recipe domain) {
        if (domain == null) return null;
        return new RecipeEntity(domain.getId(), domain.getName(), domain.getType(), domain.getSteps(), 0);
    }
}

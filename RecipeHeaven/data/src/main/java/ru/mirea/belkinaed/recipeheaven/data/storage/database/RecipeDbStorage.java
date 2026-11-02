package ru.mirea.belkinaed.recipeheaven.data.storage.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.belkinaed.recipeheaven.data.storage.RecipeStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.models.Recipe;

public class RecipeDbStorage implements RecipeStorage {

    private final RecipeDao recipeDao;

    public RecipeDbStorage(Context context) {
        this.recipeDao = AppDatabase.getInstance(context).recipeDao();
    }

    public RecipeDbStorage(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public Recipe getRecipeById(int id) {
        if (recipeDao == null) return null;
        RecipeEntity entity = recipeDao.getRecipeById(id);
        return mapToStorageModel(entity);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        if (recipeDao == null) return new ArrayList<>();
        List<RecipeEntity> entities = recipeDao.getAllRecipes();
        return mapToStorageModelList(entities);
    }

    @Override
    public List<Recipe> getRecipesByWeatherType(String type) {
        if (recipeDao == null) return new ArrayList<>();
        List<RecipeEntity> entities = recipeDao.getRecipesByWeatherType(type);
        return mapToStorageModelList(entities);
    }

    @Override
    public List<Recipe> getRecipesByUserId(int userId) {
        if (recipeDao == null) return new ArrayList<>();
        List<RecipeEntity> entities = recipeDao.getRecipesByUserId(userId);
        return mapToStorageModelList(entities);
    }

    @Override
    public boolean insertRecipe(Recipe recipe) {
        if (recipeDao == null || recipe == null) return false;
        try {
            RecipeEntity entity = mapToEntity(recipe);
            recipeDao.insertRecipe(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRecipe(Recipe recipe) {
        if (recipeDao == null || recipe == null) return false;
        try {
            RecipeEntity entity = mapToEntity(recipe);
            recipeDao.updateRecipe(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeDao == null) return false;
        try {
            recipeDao.deleteRecipeById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Recipe mapToStorageModel(RecipeEntity entity) {
        if (entity == null) return null;
        return new Recipe(entity.getId(), entity.getName(), entity.getWeatherType(), entity.getSteps());
    }

    private List<Recipe> mapToStorageModelList(List<RecipeEntity> entities) {
        List<Recipe> list = new ArrayList<>();
        if (entities != null) {
            for (RecipeEntity entity : entities) {
                list.add(mapToStorageModel(entity));
            }
        }
        return list;
    }

    private RecipeEntity mapToEntity(Recipe storageModel) {
        if (storageModel == null) return null;
        return new RecipeEntity(
                storageModel.getId(),
                storageModel.getName(),
                storageModel.getType(),
                storageModel.getSteps(),
                0
        );
    }
}

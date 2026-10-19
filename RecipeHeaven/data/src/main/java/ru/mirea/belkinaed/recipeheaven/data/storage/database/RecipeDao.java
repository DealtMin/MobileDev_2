package ru.mirea.belkinaed.recipeheaven.data.storage.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    RecipeEntity getRecipeById(int id);

    @Query("SELECT * FROM recipes")
    List<RecipeEntity> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE weatherType = :type")
    List<RecipeEntity> getRecipesByWeatherType(String type);

    @Query("SELECT * FROM recipes WHERE userId = :userId")
    List<RecipeEntity> getRecipesByUserId(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeEntity recipe);

    @Update
    void updateRecipe(RecipeEntity recipe);

    @Query("DELETE FROM recipes WHERE id = :id")
    void deleteRecipeById(int id);
}

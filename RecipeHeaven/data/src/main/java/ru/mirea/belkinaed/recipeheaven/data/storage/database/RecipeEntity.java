package ru.mirea.belkinaed.recipeheaven.data.storage.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String weatherType;
    private String steps;
    private int userId;

    public RecipeEntity(int id, String name, String weatherType, String steps, int userId) {
        this.id = id;
        this.name = name;
        this.weatherType = weatherType;
        this.steps = steps;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

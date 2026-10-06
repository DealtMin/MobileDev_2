package ru.mirea.belkinaed.recipeheaven.domain.models;

public class Recipe {
    private int id;
    private String name;
    private String weaterType;
    private String steps;

    public Recipe(int id, String name, String type, String steps) {
        this.id = id;
        this.name = name;
        this.weaterType = type;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public String getType() {
        return weaterType;
    }

}

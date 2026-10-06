package ru.mirea.belkinaed.recipeheaven.domain.models;

public class User {
    private int id;
    private String name;
    private String about;

    public User(int id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }


}

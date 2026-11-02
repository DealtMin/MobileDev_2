package ru.mirea.belkinaed.recipeheaven.data.storage.models;

public class User {
    private final String name;
    private final String description;
    private String email;
    private String password;

    public User(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public User(String name, String description, String email, String password) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

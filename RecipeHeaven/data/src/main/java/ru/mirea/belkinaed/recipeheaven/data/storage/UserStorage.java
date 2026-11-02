package ru.mirea.belkinaed.recipeheaven.data.storage;

import ru.mirea.belkinaed.recipeheaven.data.storage.models.User;

public interface UserStorage {
    boolean save(User user);
    User get();
    boolean createUser(User user);
    boolean createUser(String email, String password);
    boolean logIn(String email, String password);
    boolean logOut();
}

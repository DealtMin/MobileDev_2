package ru.mirea.belkinaed.recipeheaven.domain.repository;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;

public interface UserRepository {
    User getUser();
    boolean UpdateUser(User data);
    boolean CreateUser(User data);
    boolean CreateUser(String email, String password);
    boolean LogIn(String email, String password);
    boolean LogOut();
}

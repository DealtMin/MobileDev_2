package ru.mirea.belkinaed.recipeheaven.domain.repository;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;

public interface UserRepository {
    User getUser(int id);
    boolean UpdateUser(User data);
    boolean CreateUser(User data);
    boolean LogIn(String name,String password);
    boolean LogOut(int id);
}

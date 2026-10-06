package ru.mirea.belkinaed.recipeheaven.data.repository;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private User fake = new User(1,"ivan", "about ivan");
    @Override
    public User getUser(int id) {
        return fake;
    }

    @Override
    public boolean UpdateUser(User data) {
        return false;
    }

    @Override
    public boolean CreateUser(User data) {
        return false;
    }

    @Override
    public boolean LogIn(String name, String password) {
        return false;
    }

    @Override
    public boolean LogOut(int id) {
        return false;
    }
}

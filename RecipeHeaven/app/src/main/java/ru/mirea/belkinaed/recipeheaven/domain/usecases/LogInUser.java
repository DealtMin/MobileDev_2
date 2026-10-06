package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class LogInUser {
    private UserRepository userRepository;

    public LogInUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(int id){
        return userRepository.getUser(id);
    }
}

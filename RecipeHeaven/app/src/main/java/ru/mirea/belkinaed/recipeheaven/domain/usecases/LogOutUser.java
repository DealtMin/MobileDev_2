package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class LogOutUser {
    private UserRepository userRepository;

    public LogOutUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(int id){
        userRepository.LogOut(id);
    }
}

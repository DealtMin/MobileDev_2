package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class LogInUserUseCase {
    private UserRepository userRepository;

    public LogInUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String name, String password){
        return userRepository.LogIn(name, password);
    }
}

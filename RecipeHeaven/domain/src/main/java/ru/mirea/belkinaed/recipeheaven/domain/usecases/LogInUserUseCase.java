package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class LogInUserUseCase {
    private UserRepository userRepository;

    public LogInUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email, String password){
        return userRepository.LogIn(email, password);
    }
}

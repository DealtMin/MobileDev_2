package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class LogOutUserUseCase {
    private UserRepository userRepository;

    public LogOutUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(){
        userRepository.LogOut();
    }
}

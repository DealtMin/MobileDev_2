package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class CreateUserUseCase {
    private UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(User data){
        return userRepository.CreateUser(data);
    }
}

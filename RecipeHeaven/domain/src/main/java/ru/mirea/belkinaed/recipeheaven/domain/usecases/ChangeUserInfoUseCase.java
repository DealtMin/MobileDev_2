package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.User;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;

public class ChangeUserInfoUseCase {
    private UserRepository userRepository;

    public ChangeUserInfoUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(User data){
        return userRepository.UpdateUser(data);
    }
}

package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.recipeheaven.data.repository.UserRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.CreateUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogInUserUseCase;

public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AuthViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            UserRepository userRepository = new UserRepositoryImpl(context);
            GetUserUseCase getUserUseCase = new GetUserUseCase(userRepository);
            LogInUserUseCase logInUserUseCase = new LogInUserUseCase(userRepository);
            CreateUserUseCase createUserUseCase = new CreateUserUseCase(userRepository);
            return (T) new AuthViewModel(getUserUseCase, logInUserUseCase, createUserUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

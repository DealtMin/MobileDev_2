package ru.mirea.belkinaed.recipeheaven.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.belkinaed.recipeheaven.data.repository.RecipeRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.data.repository.UserRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.data.repository.WeatherRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.CreateRecipeUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetFilteredRecipesUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetRecipesUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetWeatherUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogOutUserUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public MainViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            UserRepository userRepository = new UserRepositoryImpl(context);
            RecipeRepository recipeRepository = new RecipeRepositoryImpl(context);
            WeatherRepository weatherRepository = new WeatherRepositoryImpl();

            LogOutUserUseCase logOutUserUseCase = new LogOutUserUseCase(userRepository);
            GetUserUseCase getUserUseCase = new GetUserUseCase(userRepository);
            GetRecipesUseCase getRecipesUseCase = new GetRecipesUseCase(recipeRepository);
            CreateRecipeUseCase createRecipeUseCase = new CreateRecipeUseCase(recipeRepository);
            GetWeatherUseCase getWeatherUseCase = new GetWeatherUseCase(weatherRepository);
            GetFilteredRecipesUseCase getFilteredRecipesUseCase = new GetFilteredRecipesUseCase(recipeRepository, weatherRepository);

            return (T) new MainViewModel(
                    logOutUserUseCase,
                    getUserUseCase,
                    getRecipesUseCase,
                    createRecipeUseCase,
                    getWeatherUseCase,
                    getFilteredRecipesUseCase
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

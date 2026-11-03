package ru.mirea.belkinaed.recipeheaven.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.belkinaed.recipeheaven.data.repository.RecipeRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.data.repository.UserRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.data.repository.WeatherRepositoryImpl;
import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.UserRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogOutUserUseCase;

public class MainViewModel extends AndroidViewModel {

    private final LogOutUserUseCase logOutUserUseCase;
    private final RecipeRepository recipeRepository;
    private final WeatherRepository weatherRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> logOutSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> networkWeatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Recipe>> dbRecipesLiveData = new MutableLiveData<>();

    private final MediatorLiveData<CombinedRecipeData> combinedDataLiveData = new MediatorLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        UserRepository userRepository = new UserRepositoryImpl(application);
        this.logOutUserUseCase = new LogOutUserUseCase(userRepository);
        this.recipeRepository = new RecipeRepositoryImpl(application);
        this.weatherRepository = new WeatherRepositoryImpl();

        setupMediatorLiveData();
        loadData();
    }

    private void setupMediatorLiveData() {
        combinedDataLiveData.addSource(networkWeatherLiveData, weather ->
                updateCombinedData(weather, dbRecipesLiveData.getValue())
        );

        combinedDataLiveData.addSource(dbRecipesLiveData, recipes ->
                updateCombinedData(networkWeatherLiveData.getValue(), recipes)
        );
    }

    private void updateCombinedData(String rawWeather, List<Recipe> rawRecipes) {
        String weather = (rawWeather == null) ? "sunny" : rawWeather;
        List<Recipe> recipes = (rawRecipes == null) ? new ArrayList<>() : rawRecipes;

        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe r : recipes) {
            if (r.getType() != null && r.getType().equalsIgnoreCase(weather)) {
                filteredRecipes.add(r);
            }
        }

        combinedDataLiveData.setValue(
                new CombinedRecipeData(weather, filteredRecipes.isEmpty() ? recipes : filteredRecipes)
        );
    }

    public LiveData<CombinedRecipeData> getCombinedDataLiveData() {
        return combinedDataLiveData;
    }

    public LiveData<Boolean> getLogOutSuccess() {
        return logOutSuccess;
    }

    public void loadData() {
        executor.execute(() -> {
            Recipe[] existing = recipeRepository.getRecipes();
            if (existing == null || existing.length == 0) {
                recipeRepository.CreateRecipe(new Recipe(1, "Лимонад со льдом", "sunny", "Смешать лимонный сок, воду и лед"));
                recipeRepository.CreateRecipe(new Recipe(2, "Мороженое", "sunny", "Заморозить сливки с сахаром и ягодами"));
                recipeRepository.CreateRecipe(new Recipe(3, "Горячий шоколад", "cloudy", "Растопить шоколад в горячем молоке"));
                recipeRepository.CreateRecipe(new Recipe(4, "Грибной крем-суп", "rainy", "Сварить грибной бульон, пюрировать"));
                recipeRepository.CreateRecipe(new Recipe(5, "Пряный чай", "snowy", "Заварить черный чай со специями и корицей"));
                existing = recipeRepository.getRecipes();
            }

            String weather = weatherRepository.getWeather().getWeather();
            networkWeatherLiveData.postValue(weather);

            List<Recipe> recipeList = (existing != null) ? Arrays.asList(existing) : new ArrayList<>();
            dbRecipesLiveData.postValue(recipeList);
        });
    }


    public void logout() {
        executor.execute(() -> {
            logOutUserUseCase.execute();
            logOutSuccess.postValue(true);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}

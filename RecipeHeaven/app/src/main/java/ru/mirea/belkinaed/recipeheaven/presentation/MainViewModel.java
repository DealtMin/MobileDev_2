package ru.mirea.belkinaed.recipeheaven.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.CreateRecipeUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetFilteredRecipesUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetRecipesUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetUserUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.GetWeatherUseCase;
import ru.mirea.belkinaed.recipeheaven.domain.usecases.LogOutUserUseCase;

public class MainViewModel extends ViewModel {

    private final LogOutUserUseCase logOutUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetRecipesUseCase getRecipesUseCase;
    private final CreateRecipeUseCase createRecipeUseCase;
    private final GetWeatherUseCase getWeatherUseCase;
    private final GetFilteredRecipesUseCase getFilteredRecipesUseCase;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> isUserLoggedIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logOutSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> networkWeatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Recipe>> dbRecipesLiveData = new MutableLiveData<>();

    private final MediatorLiveData<CombinedRecipeData> combinedDataLiveData = new MediatorLiveData<>();

    public MainViewModel(LogOutUserUseCase logOutUserUseCase,
                         GetUserUseCase getUserUseCase,
                         GetRecipesUseCase getRecipesUseCase,
                         CreateRecipeUseCase createRecipeUseCase,
                         GetWeatherUseCase getWeatherUseCase,
                         GetFilteredRecipesUseCase getFilteredRecipesUseCase) {
        this.logOutUserUseCase = logOutUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.getRecipesUseCase = getRecipesUseCase;
        this.createRecipeUseCase = createRecipeUseCase;
        this.getWeatherUseCase = getWeatherUseCase;
        this.getFilteredRecipesUseCase = getFilteredRecipesUseCase;

        setupMediatorLiveData();
        loadData();
        checkUserStatus();
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

        combinedDataLiveData.setValue(
                new CombinedRecipeData(weather, recipes)
        );
    }

    public LiveData<CombinedRecipeData> getCombinedDataLiveData() {
        return combinedDataLiveData;
    }

    public LiveData<Boolean> getIsUserLoggedIn() {
        return isUserLoggedIn;
    }

    public LiveData<Boolean> getLogOutSuccess() {
        return logOutSuccess;
    }

    public void checkUserStatus() {
        executor.execute(() -> {
            boolean loggedIn = getUserUseCase.execute() != null;
            isUserLoggedIn.postValue(loggedIn);
        });
    }

    public void loadData() {
        executor.execute(() -> {
            Recipe[] existing = getRecipesUseCase.execute();
            if (existing == null || existing.length == 0) {
                createRecipeUseCase.execute(new Recipe(1, "Лимонад со льдом", "sunny", "Смешать лимонный сок, воду и лед"));
                createRecipeUseCase.execute(new Recipe(2, "Мороженое", "sunny", "Заморозить сливки с сахаром и ягодами"));
                createRecipeUseCase.execute(new Recipe(3, "Горячий шоколад", "cloudy", "Растопить шоколад в горячем молоке"));
                createRecipeUseCase.execute(new Recipe(4, "Грибной крем-суп", "rainy", "Сварить грибной бульон, пюрировать"));
                createRecipeUseCase.execute(new Recipe(5, "Пряный чай", "snowy", "Заварить черный чай со специями и корицей"));
                existing = getRecipesUseCase.execute();
            }

            String weather = getWeatherUseCase.execute().getWeather();
            networkWeatherLiveData.postValue(weather);

            List<Recipe> recipeList = (existing != null) ? Arrays.asList(existing) : new ArrayList<>();
            dbRecipesLiveData.postValue(recipeList);
        });
    }

    public void filterByWeather() {
        executor.execute(() -> {
            Recipe[] filtered = getFilteredRecipesUseCase.execute();
            List<Recipe> recipeList = (filtered != null) ? Arrays.asList(filtered) : new ArrayList<>();
            dbRecipesLiveData.postValue(recipeList);
        });
    }

    public void logout() {
        executor.execute(() -> {
            logOutUserUseCase.execute();
            isUserLoggedIn.postValue(false);
            logOutSuccess.postValue(true);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}

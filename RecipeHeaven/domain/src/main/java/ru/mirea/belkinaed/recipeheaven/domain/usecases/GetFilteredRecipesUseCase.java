package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class GetFilteredRecipesUseCase {
    private final RecipeRepository recipeRepository;
    private final WeatherRepository weatherRepository;

    public GetFilteredRecipesUseCase(RecipeRepository recipeRepository, WeatherRepository weatherRepository) {
        this.recipeRepository = recipeRepository;
        this.weatherRepository = weatherRepository;
    }

    public Recipe[] execute(){
        return recipeRepository.getRecipesByWeatherType(
                weatherRepository.getWeather().getWeather()
        );
    }
}

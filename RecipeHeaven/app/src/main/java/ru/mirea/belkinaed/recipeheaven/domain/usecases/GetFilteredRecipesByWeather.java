package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Recipe;
import ru.mirea.belkinaed.recipeheaven.domain.repository.RecipeRepository;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class GetFilteredRecipesByWeather {
    private RecipeRepository recipeRepository;
    private WeatherRepository weatherRepository;

    public GetFilteredRecipesByWeather(RecipeRepository recipeRepository, WeatherRepository weatherRepository) {
        this.recipeRepository = recipeRepository;
        this.weatherRepository = weatherRepository;
    }

    public Recipe[] execute(){
        return recipeRepository.getRecipesByWeatherType(
                weatherRepository.getWeather()
        );
    }
}

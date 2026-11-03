package ru.mirea.belkinaed.recipeheaven.domain.usecases;

import ru.mirea.belkinaed.recipeheaven.domain.models.Weather;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class GetWeatherUseCase {
    private final WeatherRepository weatherRepository;

    public GetWeatherUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather execute() {
        return weatherRepository.getWeather();
    }
}

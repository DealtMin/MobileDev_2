package ru.mirea.belkinaed.recipeheaven.data.repository;

import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {
    @Override
    public String getWeather() {
        return "sunny";
    }
}

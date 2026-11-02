package ru.mirea.belkinaed.recipeheaven.data.repository;

import ru.mirea.belkinaed.recipeheaven.data.storage.WeatherStorage;
import ru.mirea.belkinaed.recipeheaven.data.storage.api.NetworkApi;
import ru.mirea.belkinaed.recipeheaven.domain.models.Weather;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {

    private final WeatherStorage weatherStorage;

    public WeatherRepositoryImpl() {
        this.weatherStorage = new NetworkApi();
    }

    public WeatherRepositoryImpl(WeatherStorage weatherStorage) {
        this.weatherStorage = weatherStorage;
    }

    @Override
    public Weather getWeather() {
        if (weatherStorage == null) {
            return new Weather("sunny");
        }
        ru.mirea.belkinaed.recipeheaven.data.storage.models.Weather storageWeather = weatherStorage.getWeather();
        if (storageWeather == null) {
            return new Weather("sunny");
        }
        return new Weather(storageWeather.getWeather());
    }
}

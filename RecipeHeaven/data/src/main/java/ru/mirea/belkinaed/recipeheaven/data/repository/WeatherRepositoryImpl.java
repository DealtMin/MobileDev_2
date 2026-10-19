package ru.mirea.belkinaed.recipeheaven.data.repository;

import ru.mirea.belkinaed.recipeheaven.data.storage.api.NetworkApi;
import ru.mirea.belkinaed.recipeheaven.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {

    private final NetworkApi networkApi;

    public WeatherRepositoryImpl() {
        this.networkApi = new NetworkApi();
    }

    public WeatherRepositoryImpl(NetworkApi networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    public String getWeather() {
        return networkApi.getWeather();
    }
}

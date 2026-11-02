package ru.mirea.belkinaed.recipeheaven.data.storage.models;

public class Weather {
    private String weather;

    public Weather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}

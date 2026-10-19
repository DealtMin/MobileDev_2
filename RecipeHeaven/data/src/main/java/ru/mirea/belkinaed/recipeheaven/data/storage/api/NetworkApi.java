package ru.mirea.belkinaed.recipeheaven.data.storage.api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NetworkApi {

    private static final String DEFAULT_API_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=55.751244&longitude=37.618423&current_weather=true";

    private final String apiUrl;
    private final ExecutorService executorService;

    public NetworkApi() {
        this(DEFAULT_API_URL);
    }

    public NetworkApi(String apiUrl) {
        this.apiUrl = apiUrl;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public String getWeather() {
        return fetchWeather();
    }

    public String fetchWeather() {
        Future<String> future = executorService.submit(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    );
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.has("current_weather")) {
                        JSONObject currentWeather = jsonResponse.getJSONObject("current_weather");
                        int weatherCode = currentWeather.optInt("weathercode", 0);
                        return parseWeatherCode(weatherCode);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return "sunny";
        });

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            return "sunny";
        }
    }

    private String parseWeatherCode(int code) {
        if (code == 0) {
            return "sunny";
        } else if (code >= 1 && code <= 3) {
            return "cloudy";
        } else if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) {
            return "rainy";
        } else if ((code >= 71 && code <= 77) || (code >= 85 && code <= 86)) {
            return "snowy";
        } else if (code >= 95) {
            return "stormy";
        }
        return "sunny";
    }
}

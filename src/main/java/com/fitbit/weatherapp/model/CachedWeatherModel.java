package com.fitbit.weatherapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Sorina23 on 7/14/2017.
 */
@Document(collection="weatherInfo")
public class CachedWeatherModel {

    @Id
    private String id;

    private Long lastUpdatedAt;
    private String city;
    private WeatherModel currentWeather;
    private List<WeatherModel> forecast;

    public CachedWeatherModel() {}

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public WeatherModel getCurrentWeather() {
        return currentWeather;
    }

    public List<WeatherModel> getForecast() {
        return forecast;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCurrentWeather(WeatherModel currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setForecast(List<WeatherModel> forecast) {
        this.forecast = forecast;
    }

    public Long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}

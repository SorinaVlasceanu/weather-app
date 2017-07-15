package com.fitbit.weatherapp.service;

import com.fitbit.weatherapp.model.WeatherModel;

import java.util.List;

/**
 * Created by Sorina23 on 7/14/2017.
 */
public interface WeatherInfoService {
    WeatherModel getCurrentWeatherByCity(String cityName);
    List<WeatherModel> getForecastWeatherByCity(String cityName);
}

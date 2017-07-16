package com.fitbit.weatherapp.service;

import com.fitbit.weatherapp.model.CachedWeatherModel;
import com.fitbit.weatherapp.model.WeatherModel;
import com.fitbit.weatherapp.repository.CachedWeatherRepository;
import com.fitbit.weatherapp.util.WWOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sorina23 on 7/14/2017.
 */

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {

    @Autowired
    private WWOHelper wwoHelper;

    @Autowired
    private CachedWeatherRepository cachedWeatherRepository;

    private static final Long INTERVAL = 60 * 60 * 1000L;
    private final Object lock = new Object();

    /**
     * Creates a base object (WeatherModel) representing the current weather
     * conditions for a given city.
     * Makes use of helper and repository methods to construct the object.
     * Limits the number of requests to the WWO API at 1/h/city
     *
     * @param cityName
     * @return WeatherModel
     */
    @Override
    public WeatherModel getCurrentWeatherByCity(String cityName) {

        WeatherModel currentWeather;
        boolean hourPassed;

        synchronized (lock) {
            CachedWeatherModel cachedWeatherModel = cachedWeatherRepository.findLastUpdatedByCity(cityName);

            if (cachedWeatherModel == null) {
                cachedWeatherModel = wwoHelper.mapCachedModel(cityName, new CachedWeatherModel());
                currentWeather = cachedWeatherRepository.save(cachedWeatherModel).getCurrentWeather();
            } else {
                hourPassed = System.currentTimeMillis() >= cachedWeatherModel.getLastUpdatedAt() + INTERVAL;
                if (hourPassed) {
                    cachedWeatherModel = wwoHelper.mapCachedModel(cityName, cachedWeatherModel);
                    currentWeather = cachedWeatherRepository.save(cachedWeatherModel).getCurrentWeather();
                } else {
                    currentWeather = cachedWeatherRepository.findCurrentWeatherByCity(cityName).getCurrentWeather();
                }

            }
        }

        return currentWeather;
    }


    /**
     * Creates a list of base objects (WeatherModel) representing the three day forecast
     * for a given city.
     * Makes use of helper and repository methods to construct the list.
     * Limits the number of requests to the WWO API at 1/h/city
     *
     * @param cityName
     * @return List<WeatherModel>
     */

    @Override
    public List<WeatherModel> getForecastWeatherByCity(String cityName) {

        List<WeatherModel> forecast;
        boolean hourPassed;

        synchronized (lock) {
            CachedWeatherModel cachedWeatherModel = cachedWeatherRepository.findLastUpdatedByCity(cityName);
            if (cachedWeatherModel == null) {
                cachedWeatherModel = wwoHelper.mapCachedModel(cityName, new CachedWeatherModel());
                forecast = cachedWeatherRepository.save(cachedWeatherModel).getForecast();
            } else {
                hourPassed = System.currentTimeMillis() >= cachedWeatherModel.getLastUpdatedAt() + INTERVAL;
                if (hourPassed) {
                    cachedWeatherModel = wwoHelper.mapCachedModel(cityName, cachedWeatherModel);
                    forecast = cachedWeatherRepository.save(cachedWeatherModel).getForecast();
                } else {
                    forecast = cachedWeatherRepository.findForecastByCity(cityName).getForecast();
                }

            }

        }

        return forecast;
    }
}

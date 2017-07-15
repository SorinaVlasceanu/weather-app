package com.fitbit.weatherapp.controller;

import com.fitbit.weatherapp.model.WeatherModel;
import com.fitbit.weatherapp.service.WeatherInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Sorina23 on 7/14/2017.
 */
@RestController
public class WeatherInfoController {

    @Autowired
    WeatherInfoServiceImpl weatherInfoServiceImpl;

    @GetMapping(value = "/current", produces = "application/json")
    public WeatherModel getCurrentWeatherByCity(@RequestParam("city") String cityName) {
        return weatherInfoServiceImpl.getCurrentWeatherByCity(cityName);
    }

    @GetMapping(value = "/forecast", produces = "application/json")
    public List<WeatherModel> getForecastByCity(@RequestParam("city") String cityName) {
        return weatherInfoServiceImpl.getForecastWeatherByCity(cityName);
    }
}

package com.fitbit.weatherapp.controller;

import com.fitbit.weatherapp.DTO.ResponseDTO;
import com.fitbit.weatherapp.model.WeatherModel;
import com.fitbit.weatherapp.service.WeatherInfoServiceImpl;
import com.fitbit.weatherapp.util.ErrorMessage;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseDTO getCurrentWeatherByCity(@RequestParam("city") String cityName) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (cityName.isEmpty()) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setNotification(ErrorMessage.NULL_CITY_PARAM.getValue());

        } else {
            try {
                WeatherModel weatherModel = weatherInfoServiceImpl.getCurrentWeatherByCity(cityName);
                responseDTO.setData(weatherModel);
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.getReasonPhrase());
            }
            catch(IllegalArgumentException e) {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                responseDTO.setNotification(e.getMessage());
            }
        }

        return responseDTO;
    }

    @GetMapping(value = "/forecast", produces = "application/json")
    public ResponseDTO getForecastByCity(@RequestParam("city") String cityName) {
        ResponseDTO responseDTO = new ResponseDTO();

        if (cityName.isEmpty()) {
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDTO.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setNotification(ErrorMessage.NULL_CITY_PARAM.getValue());

        } else {
            try {
                List<WeatherModel> forecast = weatherInfoServiceImpl.getForecastWeatherByCity(cityName);
                responseDTO.setData(forecast);
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.getReasonPhrase());
            } catch (IllegalArgumentException e) {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                responseDTO.setNotification(e.getMessage());
            }

        }
        return responseDTO;
    }
}


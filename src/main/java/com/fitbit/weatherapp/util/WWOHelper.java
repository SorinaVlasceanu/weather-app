package com.fitbit.weatherapp.util;

import com.fitbit.weatherapp.model.CachedWeatherModel;
import com.fitbit.weatherapp.model.WeatherModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorina23 on 7/14/2017.
 */

@Component
public class WWOHelper {

    private static final Logger log4j = Logger.getLogger(WWOHelper.class);

    @Value("${wwo.api.key}")
    private String wwoApiKey;

    @Value("${wwo.api.url}")
    private String wwoApiURL;

    /**
     * Constructs and returns the object that is to be stored in database.
     * Uses two other private methods to populate currentWeather and forecast fields.
     *
     * @param cityName
     * @param cachedWeatherModel
     * @return CachedWeatherModel object containing information extracted from request to WWO API
     * @throws IllegalArgumentException if the WWO API returns an error message
     */
    public CachedWeatherModel mapCachedModel(String cityName, CachedWeatherModel cachedWeatherModel) {

        JsonObject wwoJsonObject = getWWOInfo(cityName);
        JsonArray errorObject = wwoJsonObject.getAsJsonObject("data").getAsJsonArray("error");

        if (errorObject == null) {
            cachedWeatherModel.setCity(cityName);
            cachedWeatherModel.setLastUpdatedAt(System.currentTimeMillis());
            cachedWeatherModel.setCurrentWeather(mapCurrentInfo(wwoJsonObject));
            cachedWeatherModel.setForecast(mapForecastInfo(wwoJsonObject));
        } else {
            throw new IllegalArgumentException(errorObject.get(0).getAsJsonObject().get("msg").getAsString());
        }

        return cachedWeatherModel;
    }

    /**
     * Constructs and returns the object containing current weather information.
     * Information is extracted from jsonObject returned by WWO API
     *
     * @param wwoJsonObject
     * @return WeatherModel object with info about current weather
     */
    private WeatherModel mapCurrentInfo(JsonObject wwoJsonObject) {
        WeatherModel currentWeather = new WeatherModel();

        if (wwoJsonObject != null) {
            JsonObject currentCondition = wwoJsonObject.getAsJsonObject("data").getAsJsonArray("current_condition").get(0).getAsJsonObject();
            currentWeather = mapModel(currentCondition, true);
        }

        return currentWeather;
    }

    /**
     * Constructs and returns the object containing forecast information.
     * Information is extracted from jsonObject returned by WWO API
     *
     * @param wwoJsonObject
     * @return a list of WeatherModel objects with info about forecast
     */
    private List<WeatherModel> mapForecastInfo(JsonObject wwoJsonObject) {

        List<WeatherModel> forecastWeather = new ArrayList<>();

        if (wwoJsonObject != null) {
            JsonArray weather = wwoJsonObject.getAsJsonObject("data").getAsJsonArray("weather");
            JsonObject weatherObject;
            for (int i = 1; i < weather.size(); i++) {
                weatherObject = weather.get(i).getAsJsonObject().getAsJsonArray("hourly").get(0).getAsJsonObject();
                forecastWeather.add(mapModel(weatherObject, false));
            }
        }

        return forecastWeather;
    }

    /**
     * Constructs and returns the base object model
     * @param object
     * @param flag used to extract the correct key from WWO API current weather response and
     *             forecast response, respectively. The two objects returned in the response
     *             contain different keys representing the same information (temperature)
     * @return WeatherModel base object
     */
    private WeatherModel mapModel(JsonObject object, boolean flag) {
        WeatherModel weatherModel = new WeatherModel();

        weatherModel.setTempC(flag ? object.get("temp_C").getAsInt() : object.get("tempC").getAsInt());
        weatherModel.setTempF(flag ? object.get("temp_F").getAsInt() : object.get("tempF").getAsInt());
        weatherModel.setHumidity(object.get("humidity").getAsFloat() / 100);
        weatherModel.setCondition(object.getAsJsonArray("weatherDesc").get(0).getAsJsonObject().get("value").getAsString());

        return weatherModel;

    }

    /**
     * Queries WWO API for information regarding the current weather and forecast for a given city.
     * The names of the query parameters have been represented using enums to avoid errors (passing in wrong constants)
     * and document the meaning of the params in a more human-readable way
     *
     * @param cityName
     * @return com.google.gson.JsonObject used for mapping custom objects
     */
    private JsonObject getWWOInfo(String cityName) {

        RestTemplate restTemplate = new RestTemplate();

        final String reqURL = wwoApiURL + ParamKey.KEY.getValue() + "=" + wwoApiKey + "&" + ParamKey.LOCATION.getValue() + "=" + cityName + "&"
                + ParamKey.FORMAT.getValue() + "=json&" + ParamKey.NUM_DAYS.getValue() + "=4&" + ParamKey.INCLUDE_WEATHER_FORECAST.getValue() + "=yes&"
                + ParamKey.INCLUDE_MONTHLY_CLIMATE_AVG.getValue() + "=no&" + ParamKey.TIME_INTERVAL.getValue() + "=24";


        ResponseEntity<String> response = restTemplate.getForEntity(reqURL, String.class);
        log4j.info("--------------RESPONSE BODY---------------\n" + response.getBody());


        return new JsonParser().parse(response.getBody()).getAsJsonObject();

    }
}

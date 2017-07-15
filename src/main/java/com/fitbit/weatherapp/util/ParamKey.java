package com.fitbit.weatherapp.util;

/**
 * Created by Sorina23 on 7/14/2017.
 */
public enum ParamKey {

    KEY("key"), LOCATION("q"), FORMAT("format"), NUM_DAYS("num_of_days"), INCLUDE_WEATHER_FORECAST("fx"),
    INCLUDE_MONTHLY_CLIMATE_AVG("mca"), TIME_INTERVAL("tp");

    private String value;

    ParamKey(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

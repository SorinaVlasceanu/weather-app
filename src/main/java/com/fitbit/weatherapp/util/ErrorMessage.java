package com.fitbit.weatherapp.util;

/**
 * Created by Sorina23 on 7/15/2017.
 */
public enum ErrorMessage {
    NULL_CITY_PARAM("City name cannot be null!");

    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

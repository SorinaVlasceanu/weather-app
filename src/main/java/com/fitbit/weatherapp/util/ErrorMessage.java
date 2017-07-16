package com.fitbit.weatherapp.util;

/**
 * Created by Sorina23 on 7/15/2017.
 */
public enum ErrorMessage {
    NULL_OR_MISSING_CITY_PARAM("Required String parameter 'city' is null or missing!");

    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}

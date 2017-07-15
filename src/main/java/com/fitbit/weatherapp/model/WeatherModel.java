package com.fitbit.weatherapp.model;

/**
 * Created by Sorina23 on 7/14/2017.
 */
public class WeatherModel {

    private Integer tempC;
    private Integer tempF;
    private String condition;
    private Float humidity;

    public WeatherModel() {}

    public Integer getTempC() {
        return tempC;
    }

    public void setTempC(Integer tempC) {
        this.tempC = tempC;
    }

    public Integer getTempF() {
        return tempF;
    }

    public void setTempF(Integer tempF) {
        this.tempF = tempF;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
}

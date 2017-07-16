package com.fitbit.weatherapp.DTO;

import javafx.util.Pair;

/**
 * Created by Sorina23 on 7/16/2017.
 */
public class ResponseDTO {

    private Integer status;
    private String message;
    private String notification;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

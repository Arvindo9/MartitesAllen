package com.maritesallen.almanac2020.data.model.apis.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 13-12-2019
 */
public class Data {

    @SerializedName("forecast")
    @Expose
    private Forecast forecast;

    @SerializedName("animal")
    @Expose
    private Animal animal;

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}

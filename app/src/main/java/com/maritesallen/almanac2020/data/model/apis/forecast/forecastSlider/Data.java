package com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 14-12-2019
 */
public class Data {

    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }
}

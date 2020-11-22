package com.maritesallen.almanac2020.data.model.apis.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 11-01-2020
 */
public class Data {
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;

    @SerializedName("show_slider")
    @Expose
    private Integer ghostMonthStatus = 0;

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public Integer getGhostMonthStatus() {
        return ghostMonthStatus;
    }

    public void setGhostMonthStatus(Integer ghostMonthStatus) {
        this.ghostMonthStatus = ghostMonthStatus;
    }
}

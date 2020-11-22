package com.maritesallen.almanac2020.ui.dashboard.forecast.ghostMonth;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.slider.Slider;


/**
 * Author : Arvindo Mondal
 * Created on 14-12-2019
 */
public class GhostMonthViewModel {

    public ObservableField<String>  image;

    public GhostMonthViewModel(Slider model){
        image = new ObservableField<>(model.getImage());
    }

}

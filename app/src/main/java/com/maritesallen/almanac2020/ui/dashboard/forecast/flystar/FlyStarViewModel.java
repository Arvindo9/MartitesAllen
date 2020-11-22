package com.maritesallen.almanac2020.ui.dashboard.forecast.flystar;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.slider.Slider;


/**
 * Author : Arvindo Mondal
 * Created on 14-12-2019
 */
public class FlyStarViewModel {

    public ObservableField<String>  image;

    public FlyStarViewModel(Slider model){
        image = new ObservableField<>(model.getImage());
    }

}

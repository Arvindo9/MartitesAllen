package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.Slider;

/**
 * Author : Arvindo Mondal
 * Created on 14-12-2019
 */
public class ImageViewModel {

    public ObservableField<String>  image;
    public ObservableField<String>  title;
    public ObservableField<String>  body;
    public ObservableField<Integer>  imageDrawable;

    public ImageViewModel(Slider model){
        image = new ObservableField<>(model.getImage());
        title = new ObservableField<>(model.getTitle());
        body = new ObservableField<>(model.getDescription());
    }

    public ImageViewModel(Slider model, int type){
        imageDrawable = new ObservableField<>(model.getDrawable());
    }
}

package com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Unsuitable;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class UnsuitableViewModel {
    public final ObservableField<String> title;
    public final ObservableField<String> image;

    public UnsuitableViewModel(Unsuitable model){
        title = new ObservableField<>(model.getTitle());
        image = new ObservableField<>(model.getImage());
    }
}

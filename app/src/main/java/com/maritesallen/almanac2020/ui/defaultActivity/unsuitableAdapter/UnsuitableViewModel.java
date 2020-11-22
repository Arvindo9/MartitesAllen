package com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class UnsuitableViewModel {
    public final ObservableField<String> title;
    public final ObservableField<String> image;

    public UnsuitableViewModel(UnSuitableActivityList model){
        title = new ObservableField<>(model.getTitle());
        image = new ObservableField<>(model.getIconImage());
    }
}

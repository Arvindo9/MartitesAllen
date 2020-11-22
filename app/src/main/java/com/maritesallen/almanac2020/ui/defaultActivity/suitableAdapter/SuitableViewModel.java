package com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class SuitableViewModel {
    public final ObservableField<String> title;
    public final ObservableField<String> image;

    public SuitableViewModel(ActivityList model){
        title = new ObservableField<>(model.getTitle());
        image = new ObservableField<>(model.getIconImage());
    }
}

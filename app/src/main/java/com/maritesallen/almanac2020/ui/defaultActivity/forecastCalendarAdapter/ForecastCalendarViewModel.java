package com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalender;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
public class ForecastCalendarViewModel {
    public ObservableField<String> month;
    public ObservableField<String> day;

    public ForecastCalendarViewModel(ForecastCalender data) {
        month = new ObservableField<>(data.getMonth());
        day = new ObservableField<>(data.getDay());
    }
}

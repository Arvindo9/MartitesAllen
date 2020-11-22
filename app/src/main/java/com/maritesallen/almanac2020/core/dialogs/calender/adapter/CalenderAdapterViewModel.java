package com.maritesallen.almanac2020.core.dialogs.calender.adapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.db.calendar.months.Months;

public class CalenderAdapterViewModel {
    public final ObservableField<String> month;
    public final ObservableField<String> year;
    public ObservableField<Boolean> lastBorder;

    public CalenderAdapterViewModel(Months model, boolean lastBorder){
        month = new ObservableField<>(model.getMonth());
        year = new ObservableField<>(model.getYear());
        this.lastBorder = new ObservableField<>(lastBorder);
    }
}
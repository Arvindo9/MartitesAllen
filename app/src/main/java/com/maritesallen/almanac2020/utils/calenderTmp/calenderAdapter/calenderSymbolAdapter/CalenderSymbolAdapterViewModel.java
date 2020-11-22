package com.maritesallen.almanac2020.utils.calenderTmp.calenderAdapter.calenderSymbolAdapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.calender.Calenders;

/**
 * Author : Arvindo Mondal
 * Created on 02-12-2019
 */
public class CalenderSymbolAdapterViewModel {

    public final ObservableField<String> icons;

    public CalenderSymbolAdapterViewModel(Calenders data) {
        icons = new ObservableField<>();
    }
}

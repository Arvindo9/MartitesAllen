package com.maritesallen.almanac2020.core.dialogs.country.countryAdapter;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.db.flag.Flag;

/**
 * Author : Arvindo Mondal
 * Created on 29-11-2019
 */
public class CountryAdapterViewModel {

    public ObservableField<String> country;
    public ObservableField<Boolean> lastBorder;

    public CountryAdapterViewModel(Flag data, boolean lastBorder) {
        this.country = new ObservableField<>(data.getCountryName());
        this.lastBorder = new ObservableField<>(lastBorder);
    }
}

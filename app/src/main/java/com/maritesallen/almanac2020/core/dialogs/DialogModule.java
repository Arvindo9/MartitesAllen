package com.maritesallen.almanac2020.core.dialogs;

import com.maritesallen.almanac2020.core.dialogs.country.countryAdapter.CountryAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Created on 29-11-2019
 */
@Module
public class DialogModule {

    @Provides
    CountryAdapter provideCountryAdapter() {
        return new CountryAdapter(new ArrayList<>());
    }
}

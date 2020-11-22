package com.maritesallen.almanac2020.ui.defaultActivity;

import com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter.ForecastCalendarAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter.SuitableAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter.UnsuitableAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
@Module
public class DefaultModule {

    @Provides
    SuitableAdapter provideSuitableAdapter() {
        return new SuitableAdapter(new ArrayList<>());
    }

    @Provides
    UnsuitableAdapter provideUnsuitableAdapter() {
        return new UnsuitableAdapter(new ArrayList<>());
    }

    @Provides
    ForecastCalendarAdapter provideForecastCalendarAdapter() {
        return new ForecastCalendarAdapter(new ArrayList<>());
    }
}

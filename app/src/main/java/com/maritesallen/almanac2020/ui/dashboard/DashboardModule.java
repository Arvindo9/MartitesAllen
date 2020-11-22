package com.maritesallen.almanac2020.ui.dashboard;

import android.content.Context;

import com.maritesallen.almanac2020.ui.dashboard.books.adapter.BooksAdapter;
import com.maritesallen.almanac2020.ui.dashboard.calender.calenderAdapter.CalenderAdapter;
import com.maritesallen.almanac2020.ui.dashboard.calender.calenderAdapter.calenderSymbolAdapter.CalenderSymbolAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.adapter.ForecastAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.flystar.FlyStarPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ghostMonth.GhostMonthPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager.ForecastImagePager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.suitable.SuitableAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable.UnsuitableAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
@Module
public class DashboardModule {

    @Provides
    ForecastAdapter provideForecastAdapter() {
        return new ForecastAdapter(new ArrayList<>());
    }

/*
    @Provides
    ForecastPager provideForecastPager(Context context) {
        return new ForecastPager(context, new ArrayList<>());
    }
*/

    @Provides
    ForecastImagePager provideForecastImagePager(Context context) {
        return new ForecastImagePager(context, new ArrayList<>());
    }

    @Provides
    GhostMonthPager provideGhostMonthPager(Context context) {
        return new GhostMonthPager(context, new ArrayList<>());
    }

    @Provides
    FlyStarPager provideFlyStarPager(Context context) {
        return new FlyStarPager(context, new ArrayList<>());
    }
/*

    @Provides
//    ForecastPager provideForecastPager(ForecastFragment fragment) {
    ForecastPager provideForecastPager(DashboardActivity activity) {
        return new ForecastPager(activity.getSupportFragmentManager());
//        return new ForecastPager(fragment.getChildFragmentManager());
    }
*/

    @Provides
    SuitableAdapter provideSuitableAdapter() {
        return new SuitableAdapter(new ArrayList<>());
    }

    @Provides
    UnsuitableAdapter provideUnsuitableAdapter() {
        return new UnsuitableAdapter(new ArrayList<>());
    }

    @Provides
    CalenderAdapter provideCalenderAdapter() {
        return new CalenderAdapter(new ArrayList<>());
    }

    @Provides
    CalenderSymbolAdapter provideCalenderSymbolAdapter() {
        return new CalenderSymbolAdapter(new ArrayList<>());
    }

    @Provides
    BooksAdapter provideBooksAdapter(Context context) {
        return new BooksAdapter(context, new ArrayList<>());
    }
}

package com.maritesallen.almanac2020.ui.dashboard;

import com.maritesallen.almanac2020.ui.dashboard.books.BooksFragment;
import com.maritesallen.almanac2020.ui.dashboard.calender.CalenderFragment;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ForecastFragment;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.ForecastAdapterFragment;
import com.maritesallen.almanac2020.ui.dashboard.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
@Module
public abstract class DashboardProvider {

    @ContributesAndroidInjector()
    abstract ForecastFragment provideForecastFragment();

    @ContributesAndroidInjector()
    abstract ForecastAdapterFragment provideForecastAdapterFragment();

    @ContributesAndroidInjector()
    abstract CalenderFragment provideCalenderFragment();

    @ContributesAndroidInjector()
    abstract BooksFragment provideBooksFragment();

    @ContributesAndroidInjector()
    abstract ProfileFragment provideProfileFragment();
}

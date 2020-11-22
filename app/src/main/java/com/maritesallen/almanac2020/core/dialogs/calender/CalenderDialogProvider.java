package com.maritesallen.almanac2020.core.dialogs.calender;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CalenderDialogProvider {

    @ContributesAndroidInjector(modules = CalenderDialogModule.class)
    abstract CalenderDialog provideCalenderDialog();
}

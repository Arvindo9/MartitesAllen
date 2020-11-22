package com.maritesallen.almanac2020.core.dialogs.calender;

import com.maritesallen.almanac2020.core.dialogs.calender.adapter.CalenderAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CalenderDialogModule {
    @Provides
    CalenderAdapter provideCalenderAdapter() {
        return new CalenderAdapter(new ArrayList<>());
    }
}

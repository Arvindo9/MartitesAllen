package com.maritesallen.almanac2020.ui.dashboard.calender.calenderAdapter;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;

public class CalenderAdapterViewModel {

    public final ObservableArrayList<Activity> activityList;
    public final ObservableField<String> calenderDate;
    public final ObservableField<String> dutyOfficer;
    public final ObservableField<String> dayName;
    public final ObservableField<String> monthCalendarPic;
    public final ObservableField<String> moonIcon;
    public final ObservableField<String> moonStatus;

    public CalenderAdapterViewModel(CalendarModel data) {
        calenderDate = new ObservableField<>(data.getDateNo());
        dutyOfficer = new ObservableField<>(data.getDutyOfficer());
        dayName = new ObservableField<>(data.getDayName());
        activityList = new ObservableArrayList<>();
        monthCalendarPic = new ObservableField<>(data.getMonthCalendarPic());
        moonIcon = new ObservableField<>(data.getMoonIcon());
        moonStatus = new ObservableField<>(data.getMoonStatus());
    }

}

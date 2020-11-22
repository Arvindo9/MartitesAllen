package com.maritesallen.almanac2020.ui.dashboard.calender;

import java.util.Date;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface CalenderNavigator {
    void onDataLoad(int currentMonth, Date currentDate);

    void showMessage(int message);

    void showDialog(boolean status, boolean shouldShow);
}

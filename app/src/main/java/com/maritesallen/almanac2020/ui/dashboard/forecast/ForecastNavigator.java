package com.maritesallen.almanac2020.ui.dashboard.forecast;

import android.view.View;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface ForecastNavigator {
    void onDateChangeClick();

    void onUnSuitableInfoClick(View view);

    void onSuitableInfoClick(View view);

    void isPremiumAccount(boolean premium);

    void removeSlotForecastStar();

    void showMessage(String message);

    void reset(boolean b);

    void showMessage(int messageId);

    void forecastDialog(boolean status);

    void ghostMonthStatus(Boolean status);

    void offlineMode(boolean status);
}

package com.maritesallen.almanac2020.ui.access.registrationFacebook;

import android.view.View;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface RegistrationFacebookNavigation {
    void onDateClick(View view);

    void onTimeClick(View view);

    void onCountryClick(View view);

    void onLoginUpClick(View view);

    void openDashboard();

    void onLoginError(String message);

    void showMessage(int message);
}

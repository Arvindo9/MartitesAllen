package com.maritesallen.almanac2020.ui.access.accessDefault;

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
interface AccessDefaultNavigator {
    void onLoginFacebookClick(View view);

    void onSignUpClick(View view);

    void onLoginInClick(View view);

    void onLoginGuestClick(View view);

    void openDashboard();

    void onLoginError(String message);

    void openFacebookForm(String email);

    void showMessage(int message);
}

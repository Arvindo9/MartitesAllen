package com.maritesallen.almanac2020.ui.access.login;

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
interface LoginNavigator {

    void onForgetPasswordClick(View view);

    void onLoginClick(View view);

    void onShowPasswordClick();

    void onRememberClick();

    void onSignUpClick(View view);

    void onTermsClick();

    void onLoginSuccess();

    void onLoginError(String message);

    void showMessage(int message);
}

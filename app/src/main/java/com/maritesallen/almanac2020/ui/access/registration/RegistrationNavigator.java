package com.maritesallen.almanac2020.ui.access.registration;

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
interface RegistrationNavigator {
    void onShowPasswordClick();

    void onShowConfirmPasswordClick();

    void onSignUpClick();

    void onDateClick();

    void onTimeClick();

    void onTermsClick();

    void onCountryClick();

    void onRegistrationSuccess();

    void onRegistrationError(String message);

    void showMessage(int message);
}

package com.maritesallen.almanac2020.ui.access.forgetPassword;

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
interface ForgetPasswordNavigator {
    void onSendEmailClick(View view);

    void onSuccess(String message);

    void onError(String message);

    void showMessage(int network_error);
}

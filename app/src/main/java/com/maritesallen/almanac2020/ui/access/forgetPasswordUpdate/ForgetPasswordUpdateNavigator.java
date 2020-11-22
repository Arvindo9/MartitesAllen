package com.maritesallen.almanac2020.ui.access.forgetPasswordUpdate;

import android.view.View;

/**
 * Author : Arvindo Mondal
 * Created on 18-12-2019
 */
interface ForgetPasswordUpdateNavigator {
    void onSuccess(String message);

    void onError(String message);

    void onShowPasswordClick();

    void onSendEmailClick(View view);

    void showMessage(int message);
}

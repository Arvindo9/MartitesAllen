package com.maritesallen.almanac2020.ui.dashboard;

import android.view.View;

/**
 * Author       : Arvindo Mondal
 * Created on   : 12-11-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
interface DashboardNavigation {
    void onForecastClick(View view);

    void onCalenderClick(View view, boolean isAccessible, int mode);

    void onBooksClick(View view);

    void onProfileClick(View view, boolean isAccessible);

    void doLogout(boolean isLogout, String message);

    void refreshBooks(int bookId);

    void showMessage(int message);

    void onPemiumPurchaseSuccess(boolean status);

    void downloadFileStatus(boolean object);

    void showMessage(String message);

    void restoreSuccessful(boolean status);

    void puchPurchaseLoader(boolean status);

    void downloadStatus(boolean status);

    void downloadProgress(int currentProgress);

    void openPlayStore();

    void unauthorosizePayment(boolean status);

    void blockUser(String status);

//    void startDownloading(ResponseBody object, Books model);
}

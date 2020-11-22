package com.maritesallen.almanac2020.ui.dashboard.profile;


import android.view.View;

import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface ProfileNavigator {
    void onUpgradeClick(View view);

    void onRestoreClick(View view);

    void onTermsClick(View view);

    void onPrivacyClick(View view);

    void onEditProfileCancelClick();

    void onEditProfileSaveClick();

    void onChangeProfileImageClick();

    void onEditProfileClick();

    void onAppInfoClick(View view);

    void onAboutClick(View view);

    void editCompleted();

    void onDateClick(View view);

    void showMessage(int message);

    void downloadBooks(List<BookPremium> bookPremium);

    void offlineMode(boolean status);
}

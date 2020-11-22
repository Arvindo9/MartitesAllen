package com.maritesallen.almanac2020.di.builder;

import com.maritesallen.almanac2020.core.dialogs.DialogProvider;
import com.maritesallen.almanac2020.core.dialogs.calender.CalenderDialogProvider;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionProvider;
import com.maritesallen.almanac2020.core.dialogs.upgrade.UpgradeProvider;
import com.maritesallen.almanac2020.ui.access.AccessActivity;
import com.maritesallen.almanac2020.ui.access.AccessProvider;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.ui.dashboard.DashboardModule;
import com.maritesallen.almanac2020.ui.dashboard.DashboardProvider;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultActivity;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultModule;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultProvider;
import com.maritesallen.almanac2020.ui.pdfViewer.PdfViewerActivity;
import com.maritesallen.almanac2020.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author       : Arvindo Mondal
 * Created on   : 09-05-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {AccessProvider.class, TermsConditionProvider.class, DialogProvider.class})
    abstract AccessActivity bindAccessActivity();

    @ContributesAndroidInjector(modules = {DashboardProvider.class, DashboardModule.class,
            CalenderDialogProvider.class, TermsConditionProvider.class, UpgradeProvider.class, DialogProvider.class})
    abstract DashboardActivity bindDashboardActivity();

    @ContributesAndroidInjector(modules = {DefaultModule.class, DefaultProvider.class})
    abstract DefaultActivity bindDefaultActivity();

    @ContributesAndroidInjector()
    abstract PdfViewerActivity bindPdfViewerActivity();
}

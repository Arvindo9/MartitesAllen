package com.maritesallen.almanac2020.core.dialogs;

import com.maritesallen.almanac2020.core.dialogs.book.BookPurchaseDialog;
import com.maritesallen.almanac2020.core.dialogs.country.CountryDialog;
import com.maritesallen.almanac2020.core.dialogs.dutyOfficer.DutyOfficerDialog;
import com.maritesallen.almanac2020.core.dialogs.guest.GuestDialog;
import com.maritesallen.almanac2020.core.dialogs.premiumAlert.PremiumAlertDialog;
import com.maritesallen.almanac2020.core.dialogs.sessionExpire.SessionExpireDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogProvider {

    @ContributesAndroidInjector()
    abstract SessionExpireDialog provideSessionExpireDialog();

    @ContributesAndroidInjector()
    abstract PremiumAlertDialog providePremiumAlertDialog();

    @ContributesAndroidInjector()
    abstract DutyOfficerDialog provideDutyOfficerDialog();

    @ContributesAndroidInjector()
    abstract GuestDialog provideGuestDialog();

    @ContributesAndroidInjector()
    abstract BookPurchaseDialog provideBookPurchaseDialog();

    @ContributesAndroidInjector(modules = DialogModule.class)
    abstract CountryDialog provideCountryDialog();
}

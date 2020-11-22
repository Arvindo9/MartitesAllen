package com.maritesallen.almanac2020.ui.access;

import com.maritesallen.almanac2020.ui.access.accessDefault.AccessDefaultFragment;
import com.maritesallen.almanac2020.ui.access.forgetPassword.ForgetPasswordFragment;
import com.maritesallen.almanac2020.ui.access.forgetPasswordUpdate.ForgetPasswordUpdateFragment;
import com.maritesallen.almanac2020.ui.access.login.LoginFragment;
import com.maritesallen.almanac2020.ui.access.registration.RegistrationFragment;
import com.maritesallen.almanac2020.ui.access.registrationFacebook.RegistrationFacebookFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

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
@Module
public abstract class AccessProvider {

    @ContributesAndroidInjector()
    abstract AccessDefaultFragment provideAccessDefaultFragment();

    @ContributesAndroidInjector()
    abstract ForgetPasswordFragment provideForgetPasswordFragment();

    @ContributesAndroidInjector()
    abstract LoginFragment provideLoginFragment();

    @ContributesAndroidInjector()
    abstract RegistrationFragment provideRegistrationFragment();

    @ContributesAndroidInjector()
    abstract RegistrationFacebookFragment provideRegistrationFacebookFragment();

    @ContributesAndroidInjector()
    abstract ForgetPasswordUpdateFragment provideForgetPasswordUpdateFragment();

}

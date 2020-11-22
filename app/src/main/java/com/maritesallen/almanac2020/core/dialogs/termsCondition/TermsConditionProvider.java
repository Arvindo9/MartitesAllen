package com.maritesallen.almanac2020.core.dialogs.termsCondition;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TermsConditionProvider {

    @ContributesAndroidInjector()
    abstract TermsConditionDialog provideTermsConditionDialog();
}

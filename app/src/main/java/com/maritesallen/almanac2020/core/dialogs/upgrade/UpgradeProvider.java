package com.maritesallen.almanac2020.core.dialogs.upgrade;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UpgradeProvider {

    @ContributesAndroidInjector()
    abstract UpgradeDialog provideUpgradeDialog();
}

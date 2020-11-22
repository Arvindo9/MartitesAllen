package com.maritesallen.almanac2020.core.dialogs.upgrade;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

public class UpgradeViewModel extends BaseViewModel<UpgradeNavigator> {

    public UpgradeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    //Resources---------------------------

    public void onPurchaseClick(){getNavigator().onPurchaseClick();}
    public void onCancelClick(){getNavigator().onCancelClick();}

    public void cancel() {
        setIsLoading(false);
    }
}

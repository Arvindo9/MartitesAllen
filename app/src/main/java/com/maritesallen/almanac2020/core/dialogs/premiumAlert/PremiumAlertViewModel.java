package com.maritesallen.almanac2020.core.dialogs.premiumAlert;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

public class PremiumAlertViewModel extends BaseViewModel<PremiumAlertNavigator> {

    public PremiumAlertViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    //Resource----------

    public void onProceedClick(){getNavigator().onProceedClick();}
    public void onCancelClick(){getNavigator().onCancelClick();}

    public void checkLoginMode() {
        if(getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()){
            getNavigator().openPremiumAlertDialogProfile();
        }
    }

    public void cancel() {
        setIsLoading(false);
    }
}

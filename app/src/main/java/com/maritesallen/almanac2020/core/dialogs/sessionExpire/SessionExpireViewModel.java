package com.maritesallen.almanac2020.core.dialogs.sessionExpire;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

public class SessionExpireViewModel extends BaseViewModel<SessionExpireNavigator> {

    public SessionExpireViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    //Resource----------

    public void onProceedClick(){getNavigator().onProceedClick();}
    public void onCancelClick(){getNavigator().onCancelClick();}

    public void cancel() {
        setIsLoading(false);
    }
}

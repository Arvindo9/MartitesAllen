package com.maritesallen.almanac2020.core.dialogs.guest;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Created on 10-12-2019
 */
public class GuestViewModel extends BaseViewModel<GuestNavigator> {

    public GuestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }
    public void onProceedClick(){
        getNavigator().onProceedClick();
    }

    public void cancel() {
        setIsLoading(false);
    }
}

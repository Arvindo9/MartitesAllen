package com.maritesallen.almanac2020.core.dialogs.dutyOfficer;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
public class DutyOfficerViewModel extends BaseViewModel<DutyOfficerNavigator> {

    public final ObservableField<String> title;
    public final ObservableField<String> body;

    public DutyOfficerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        title = new ObservableField<>();
        body = new ObservableField<>();
    }

    void setData(String title, String body){
        this.title.set(title);
        this.body.set(body);
    }

    //resource------------------

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }

    public void cancel() {
        setIsLoading(false);
    }
}

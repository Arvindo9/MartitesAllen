package com.maritesallen.almanac2020.core.dialogs.alert;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

/**
 * Author       : Arvindo Mondal
 * Created on   : 26-08-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class AlertViewModel extends BaseViewModel<AlertNavigator> {
    public final ObservableField title;
    public final ObservableField heading;
    public final ObservableField body;

    public AlertViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        title = new ObservableField();
        heading = new ObservableField();
        body = new ObservableField();
    }

    //Resource-----------------------

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }
    public void onYesClick(){
        getNavigator().onYesClick();
    }
    public void onNoClick(){
        getNavigator().onNoClick();
    }
}

package com.maritesallen.almanac2020.core.dialogs.calender;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.calendar.months.Months;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

public class CalenderDialogViewModel extends BaseViewModel<CalenderNavigator> {
    public final ObservableList<Months> modelObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<Months>> modelLiveData;

    public CalenderDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelLiveData = new MutableLiveData<>();
    }


    //List view---------------------------------------

    private ObservableList<Months> getObservableList() {
        return modelObservableList;
    }

    void addDataToList(List<Months> list) {
        modelObservableList.clear();
        modelObservableList.addAll(list);
    }

    MutableLiveData<List<Months>> getLiveData() {
        return modelLiveData;
    }

    private void setLiveData(List<Months> list){
        modelLiveData.setValue(list);
    }

    void loadAdapterData(String[] months) {
        List<Months> list = new ArrayList<>();
        String year = "2020";

        for(int i = 0; i < months.length; i++){
            list.add(new Months(i, months[i], year));
        }

        modelLiveData.setValue(list);
    }






    public void onCancelClick(){
        getNavigator().onCancelClick();
    }

    public void cancel() {
        setIsLoading(false);
    }
}

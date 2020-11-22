package com.maritesallen.almanac2020.ui.dashboard.calender;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.util.General;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class CalenderViewModel extends BaseViewModel<CalenderNavigator> {

    public final ObservableList<CalendarModel> modelObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<CalendarModel>> modelLiveData;

    public CalenderViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelLiveData = new MutableLiveData<>();
    }

    //List view---------------------------------------

    private ObservableList<CalendarModel> getObservableList() {
        return modelObservableList;
    }

    void addDataToList(List<CalendarModel> list) {
        modelObservableList.clear();
        modelObservableList.addAll(list);

//        getNavigator().onDataLoad(General.getCurrentMonth(), General.getCurrentDate());
    }

    MutableLiveData<List<CalendarModel>> getLiveData() {
        return modelLiveData;
    }

    private void setLiveData(List<CalendarModel> list){
        modelLiveData.setValue(list);
    }

    int getListPosition(int monthId, int day) {
        int _id = 0;
        boolean ok = false;
        for(int i = 0; i < modelObservableList.size() && !ok; i++ ){
            if(modelObservableList.get(i) != null && modelObservableList.get(i).getMonthId() == monthId){
                if(modelObservableList.get(i).getDateInt() != null && modelObservableList.get(i).getDateInt() == day) {
                    ok = true;
                    _id = i;
                }
            }
        }

        return _id;
    }

    // Services----------------------------------------

    void getCalendarDb(){
        int months = General.getCurrentMonth()+1;
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCalendar(months)
                .map(list -> {
                    if(list != null && !list.isEmpty()){
                        int c = 0;
                        int size = list.size();
                        for(int i=0; i < size; i++) {
                            if (c == 10) {
                                c = 0;
                                list.add(i, null);
                            }
                            c++;
                        }
                    }
                    return list;
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(!response.isEmpty()){
                            modelLiveData.setValue(response);
                            getCalendarApi(months, false);
                        }
                        else {
                            getCalendarApi(months, false);
                        }
                    }
                    else {
                        getCalendarApi(months, false);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    throwable.printStackTrace();
                    setIsLoading(false);
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }

    private void saveCalendar(List<CalendarModel> list){
        getCompositeDisposable().add(getDataManager()
                .saveCalendar(list)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.e("Calendar save successful");
                }, Throwable::printStackTrace));
    }

    void getCalendarApi(int monthId, boolean shouldShow) {
        String year = getDataManager().getYearId();

        getNavigator().showDialog(true, shouldShow);
        getCompositeDisposable().add(getDataManager()
                .calendar(year, String.valueOf(monthId))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getCalendarData() != null) {
                            ArrayList<CalendarModel> list = new ArrayList<>();

                            for (int i = 0; response.getCalendarData().getMonths() != null &&
                                    i < response.getCalendarData().getMonths().size(); i++) {
                                CalendarModel month = response.getCalendarData().getMonths().get(i);
                                month.setMonthCalendar(true);
                                list.add(month);

                                if (response.getCalendarData().getMonths().get(i).getMonthId() == 1) {
                                    list.addAll(response.getCalendarData().getJanuary());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 2) {
                                    list.addAll(response.getCalendarData().getFebruary());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 3) {
                                    list.addAll(response.getCalendarData().getMarch());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 4) {
                                    list.addAll(response.getCalendarData().getApril());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 5) {
                                    list.addAll(response.getCalendarData().getMay());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 6) {
                                    list.addAll(response.getCalendarData().getJune());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 7) {
                                    list.addAll(response.getCalendarData().getJuly());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 8) {
                                    list.addAll(response.getCalendarData().getAugust());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 9) {
                                    list.addAll(response.getCalendarData().getSeptember());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 10) {
                                    list.addAll(response.getCalendarData().getOctober());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 11) {
                                    list.addAll(response.getCalendarData().getNovember());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 12) {
                                    list.addAll(response.getCalendarData().getDecember());
                                }
                            }

                            modelLiveData.setValue(list);

                            if(monthId == General.getCurrentMonth()+1) {
                                clearCalendar(list);
                            }
                        }
                    }

                    getNavigator().showDialog(false, shouldShow);
                }, throwable ->{
                    throwable.printStackTrace();
                    if(shouldShow && throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }

                    getNavigator().showDialog(false, shouldShow);
                }));

    }

    private void clearCalendar(ArrayList<CalendarModel> list){
        getCompositeDisposable().add(getDataManager()
                .deleteCalendar()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response){
                        //Delete successfully
                        saveCalendar(list);
                    }
                }, throwable -> {
                    saveCalendar(list);
                }));
    }


    public String getAdsUnitId() {
        return getDataManager().getAdsUnitId();
    }

}

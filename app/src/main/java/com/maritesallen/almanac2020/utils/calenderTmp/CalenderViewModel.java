package com.maritesallen.almanac2020.utils.calenderTmp;

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
    private final MutableLiveData<List<CalendarModel>> modelLiveData  = new MutableLiveData<>();

    public CalenderViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    //List view---------------------------------------

    private ObservableList<CalendarModel> getObservableList() {
        return modelObservableList;
    }

    void addDataToList(List<CalendarModel> list) {
        modelObservableList.clear();
        modelObservableList.addAll(list);

        getNavigator().onDataLoad(General.getCurrentMonth(), General.getCurrentDate());
    }

    MutableLiveData<List<CalendarModel>> getLiveData() {
        return modelLiveData;
    }

    private void setLiveData(List<CalendarModel> list){
        modelLiveData.setValue(list);
    }


    void getCalendarDb(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCalendar()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(!response.isEmpty()){
                            modelLiveData.setValue(response);
                        }
                        else {
                            getCalendarApi();
                        }
                    }
                    else {
                        getCalendarApi();
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

    private void getCalendarApi() {
        String year = getDataManager().getYearId();

        getNavigator().showDialog(true);
        getCompositeDisposable().add(getDataManager()
                .calendar(year)
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
                            saveCalendar(list);
                        }
                    }

                    getNavigator().showDialog(false);
                }, throwable ->{
                    throwable.printStackTrace();
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }

                    getNavigator().showDialog(false);
                }));
    }

    int getListPosition(int monthId, int day) {
        int _id = 0;
        boolean ok = false;
        for(int i = 0; i < modelObservableList.size() && !ok; i++ ){
            if(modelObservableList.get(i).getMonthId() == monthId){
                if(modelObservableList.get(i).getDateInt() != null && modelObservableList.get(i).getDateInt() == day) {
                    ok = true;
                    _id = i;
                }
            }
        }

        return _id;
    }

    //Resources------------------------------------
}


/*

    void getCalendarApi() {
        String year = getDataManager().getYearId();

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .calendar(year)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getCalendarData() != null) {
                            ArrayList<CalendarModel> list = new ArrayList<>();

                            if (getDataManager().isPremium()) {
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
                            }

                            else{
                                int c = 0;
                                for(int i = 0; response.getCalendarData().getMonths()!=null &&
                                        i < response.getCalendarData().getMonths().size(); i++){
                                    CalendarModel month = response.getCalendarData().getMonths().get(i);
                                    month.setMonthCalendar(true);
                                    list.add(month);

                                    if(c == 10){
                                        c = 0;
                                        list.add(null);
                                    }
                                    c++;

                                    if(response.getCalendarData().getMonths().get(i).getMonthId() == 1){
                                        list.addAll(response.getCalendarData().getJanuary());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 2){
                                        list.addAll(response.getCalendarData().getFebruary());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 3){
                                        list.addAll(response.getCalendarData().getMarch());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 4){
                                        list.addAll(response.getCalendarData().getApril());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 5){
                                        list.addAll(response.getCalendarData().getMay());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 6){
                                        list.addAll(response.getCalendarData().getJune());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 7){
                                        list.addAll(response.getCalendarData().getJuly());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 8){
                                        list.addAll(response.getCalendarData().getAugust());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 9){
                                        list.addAll(response.getCalendarData().getSeptember());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 10){
                                        list.addAll(response.getCalendarData().getOctober());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 11){
                                        list.addAll(response.getCalendarData().getNovember());
                                    }
                                    else if(response.getCalendarData().getMonths().get(i).getMonthId() == 12){
                                        list.addAll(response.getCalendarData().getDecember());
                                    }
                                }
                            }

                            modelLiveData.setValue(list);
                        }
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
    */

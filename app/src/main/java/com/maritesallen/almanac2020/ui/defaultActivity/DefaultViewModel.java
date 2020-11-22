package com.maritesallen.almanac2020.ui.defaultActivity;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Day;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalender;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Month;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import static com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter.ForecastCalendarAdapter.TYPE_DAY;
import static com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter.ForecastCalendarAdapter.TYPE_MONTH;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
public class DefaultViewModel extends BaseViewModel<DefaultNavigator> {

    public final ObservableList<ActivityList> modelSuitableList;
    public final ObservableList<UnSuitableActivityList> modelUnsuitableList;
    public final ObservableList<ForecastCalender> modelForecastCalendarList;
    public final ObservableField<String> title;

    private final MutableLiveData<List<Month>> modelForecastCalendarLiveData;

    public DefaultViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelSuitableList = new ObservableArrayList<>();
        modelUnsuitableList = new ObservableArrayList<>();
        modelForecastCalendarList = new ObservableArrayList<>();
        modelForecastCalendarLiveData = new MutableLiveData<>();
        title = new ObservableField<>();
    }

    public MutableLiveData<List<Month>> getModelForecastCalendarLiveData() {
        return modelForecastCalendarLiveData;
    }

    void setSutableData(List<ActivityList> list){
        modelSuitableList.clear();
        modelSuitableList.addAll(list);
    }

    void setUnsutableData(List<UnSuitableActivityList> list){
        modelUnsuitableList.clear();
        modelUnsuitableList.addAll(list);
    }

    public void setForecastCalendar(List<Month> list) {
        //type = 1 for month
        //type = 2 for day
        ArrayList<ForecastCalender> modelList = new ArrayList<>();
        for (Month month : list) {
            modelList.add(new ForecastCalender(TYPE_MONTH, month.getId(), month.getMonth(), month.getMonthColor()));
            for (Day day : month.getDays()) {
                modelList.add(new ForecastCalender(TYPE_DAY, day.getDay()));
            }
        }

        modelForecastCalendarList.clear();
        modelForecastCalendarList.addAll(modelList);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    void forecastCalendarDb(String iconId, String yearId, String dataType) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getForecastCalendar(iconId, dataType)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        modelForecastCalendarLiveData.setValue(response.getMonths());
                        title.set(response.getTitle());
                        setIsLoading(false);
                        return;
                    }
                    forecastCalendarApi(iconId, yearId, dataType);
                }, throwable -> {
                    forecastCalendarApi(iconId, yearId, dataType);
                    throwable.printStackTrace();
                }));
    }

    private void forecastCalendarApi(String iconId, String yearId, String dataType) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .forecastCalender(iconId, yearId, dataType)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null && response.getData().getMonths() != null) {
                            modelForecastCalendarLiveData.setValue(response.getData().getMonths());
                            title.set(response.getData().getTitle());
                            saveForecastCalendarDb(response.getData(), iconId, dataType);
                        }
                    }

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    throwable.printStackTrace();
                }));
    }


    private void saveForecastCalendarDb(Data data, String iconId, String dataType) {
        data.setIconId(iconId);
        data.setDataType(dataType);
        getCompositeDisposable().add(getDataManager()
                .saveForecastCalendar(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                }, Throwable::printStackTrace));
    }


    public void cancel() {
        setIsLoading(false);
    }
}

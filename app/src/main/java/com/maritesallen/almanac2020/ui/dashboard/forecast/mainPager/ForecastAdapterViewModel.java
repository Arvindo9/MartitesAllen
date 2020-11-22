package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastCard;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.Slider;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class ForecastAdapterViewModel extends BaseViewModel<ForecastAdapterNavigator> {
    public final ObservableField<String> dutyOfficer;
    public final ObservableField<String> dutyOfficerDescription;
    public final ObservableField<String> moonImage;
    public final ObservableField<String> moonStatus;
    public final ObservableField<String> shopNow;

    public final ObservableList<Slider> modelSliderList;
    private final MutableLiveData<List<Slider>> modelSliderLiveData;

    public ForecastAdapterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        dutyOfficer = new ObservableField<>();
        dutyOfficerDescription = new ObservableField<>("ijjjljlkjkl jlkj ");
        moonImage = new ObservableField<>();
        moonStatus = new ObservableField<>();
        shopNow = new ObservableField<>();
        modelSliderList = new ObservableArrayList<>();
        modelSliderLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Slider>> getModelSliderLiveData() {
        return modelSliderLiveData;
    }

    void addSliderData(List<Slider> list) {
        modelSliderList.clear();
        modelSliderList.addAll(list);
    }

    public void setData(ForecastCard model) {
        dutyOfficer.set(" " + model.getDutyOfficer() + " ");
        dutyOfficerDescription.set(model.getDutyOfficerDescription());
        moonImage.set(model.getMonthIcon());
        moonStatus.set(model.getMoonStatus());
        shopNow.set(model.getShowNow());
    }

    void loadSlider() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .forecastSlider()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            modelSliderLiveData.setValue(response.getData().getSlider());
                        }
                    }
                    setIsLoading(false);
                }, throwable ->{
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    void loadSliderStatic(){
        ArrayList<Slider> list = new ArrayList<>();
        list.add(new Slider(R.drawable.auspicious_symbols_1));
        list.add(new Slider(R.drawable.auspicious_symbols_2));
        list.add(new Slider(R.drawable.auspicious_symbols_3));
        list.add(new Slider(R.drawable.auspicious_symbols_4));
        list.add(new Slider(R.drawable.auspicious_symbols_5));
        list.add(new Slider(R.drawable.auspicious_symbols_6));
        list.add(new Slider(R.drawable.auspicious_symbols_7));
        list.add(new Slider(R.drawable.auspicious_symbols_8));

        modelSliderLiveData.setValue(list);
    }

    boolean isPhilippines(){
        return getDataManager().getCountryCode() != null &&getDataManager().getCountryCode().equals("175");
    }
}

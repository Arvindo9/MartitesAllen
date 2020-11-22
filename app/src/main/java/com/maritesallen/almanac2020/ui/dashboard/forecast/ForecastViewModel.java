package com.maritesallen.almanac2020.ui.dashboard.forecast;

import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Suitable;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Unsuitable;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.util.General;

import java.net.ConnectException;
import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class ForecastViewModel extends BaseViewModel<ForecastNavigator> {

    public final ObservableList<Slider> ghostMonthList = new ObservableArrayList<>();
    public final ObservableList<Slider> flyStarList = new ObservableArrayList<>();

    public final ObservableField<String> animalName = new ObservableField<>();
    public final ObservableField<String> animalImage = new ObservableField<>();
    public final ObservableList<Suitable> modelSuitableList = new ObservableArrayList<>();
    public final ObservableList<Unsuitable> modelUnsuitableList = new ObservableArrayList<>();
    public final ObservableField<Data> model = new ObservableField<>();

    public final ObservableField<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> modelMonth =
            new ObservableField<>();

    public final ObservableField<String> titleMonth = new ObservableField<>();
    public final ObservableField<String> titleConflictMonth = new ObservableField<>();
    public final ObservableField<String> titleDayAnimal = new ObservableField<>();
    public final ObservableField<String> titleDayConflictAnimal = new ObservableField<>();
    public final ObservableField<String> slotDay = new ObservableField<>();
    public final ObservableField<String> slotAnimal = new ObservableField<>();
    public final ObservableField<String> slotForecast = new ObservableField<>();
    public final ObservableField<String> slotForecastImage = new ObservableField<>();
    public final ObservableField<String> animalMonth = new ObservableField<>();
    public final ObservableField<String> animalConflictMonth = new ObservableField<>();

    private final MutableLiveData<List<Slider>> modelGhostLiveData;
    private final MutableLiveData<List<Slider>> modelFlyLiveData;
    private final MutableLiveData<List<Suitable>> modelSuitableLiveData;
    private final MutableLiveData<List<Unsuitable>> modelUnsuitableLiveData;
    private final MutableLiveData<Data> modelLiveData;
    private final MutableLiveData<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> monthModelLiveData;

    public ForecastViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelSuitableLiveData = new MutableLiveData<>();
        modelUnsuitableLiveData = new MutableLiveData<>();
        modelLiveData = new MutableLiveData<>();
        monthModelLiveData = new MutableLiveData<>();

        modelGhostLiveData = new MutableLiveData<>();
        modelFlyLiveData = new MutableLiveData<>();

        setUp();
    }

    private void setUp() {
        animalName.set(getDataManager().getAnimal());
        animalImage.set(getDataManager().getAnimalLink());
    }

    int getLogginMode(){
        return getDataManager().getLoggedInMode();
    }

    boolean isPremium(){
        return getDataManager().isPremium();
    }

    boolean isGuestLogin(){
        return getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType();
    }

    //List view---------------------------------------

    public ObservableField<Data> getModel() {
        return model;
    }

    void addGhostData(List<Slider> list) {
        ghostMonthList.clear();
        ghostMonthList.addAll(list);
    }

    void addFlyStarData(List<Slider> list) {
        flyStarList.clear();
        flyStarList.addAll(list);
    }

    void addSuitableData(List<Suitable> list) {
        modelSuitableList.clear();
        modelSuitableList.addAll(list);
    }

    void addUnsuitableData(List<Unsuitable> list) {
        modelUnsuitableList.clear();
        modelUnsuitableList.addAll(list);
    }

    void addModelData(Data model) {
        this.model.set(model);
//        animalName.set(model.getDayAnimal());
    }

    void addMonthModelData(com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data model) {
        this.modelMonth.set(model);

        titleMonth.set("Month of " +  model.getMonthAnimal() + "\n" + "Conflict month of " + model.getConflictMonthAnimal());
        titleConflictMonth.set(model.getMonthConflictAnimalImage());
        titleDayAnimal.set(model.getDayAnimal());
        titleDayConflictAnimal.set(model.getDayConflictAnimal());
        slotDay.set(model.getSlotTiming());
        slotAnimal.set(model.getSlotAminal());
        slotForecast.set(model.getSlotForecast());
        slotForecastImage.set(model.getSlotForecastIcon());
        animalMonth.set(model.getMonthAnimalImage());
        animalConflictMonth.set(model.getMonthConflictAnimalImage());
/*
        if(!model.getSlotForecastIcon().equalsIgnoreCase("luck") ||
                !model.getSlotForecastIcon().equalsIgnoreCase("lucky")){
            getNavigator().removeSlotForecastStar();
        }
        */
    }

    MutableLiveData<List<Suitable>> getModelSuitableLiveData() {
        return modelSuitableLiveData;
    }

    MutableLiveData<List<Unsuitable>> getModelUnsuitableLiveData() {
        return modelUnsuitableLiveData;
    }

    MutableLiveData<Data> getModelLiveData() {
        return modelLiveData;
    }

    MutableLiveData<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> getMonthModelLiveData() {
        return monthModelLiveData;
    }

    public MutableLiveData<List<Slider>> getModelGhostLiveData() {
        return modelGhostLiveData;
    }

    public MutableLiveData<List<Slider>> getModelFlyLiveData() {
        return modelFlyLiveData;
    }

    //Resources------------------------------------

    public void onDateChangeClick(){getNavigator().onDateChangeClick();}
    public void onUnSuitableInfoClick(View view){getNavigator().onUnSuitableInfoClick(view);}
    public void onSuitableInfoClick(View view){getNavigator().onSuitableInfoClick(view);}

    //Task-----------------------------------------

    void forcastActivity(String date){
        if(date == null || date.isEmpty()){
            date = General.getDateSend();
        }
        getForecastActivityDb(date);
        forcastActivityApi(date);
    }

    private void forcastActivityApi(String date){

//        if(date == null || date.isEmpty()){
//            date = General.getDateSend();
//        }

        if(getDataManager().isPremium()) {
            forcastMonth(date);
        }

        getNavigator().isPremiumAccount(getDataManager().isPremium());

        getNavigator().forecastDialog(true);
        String finalDate = date;
        getCompositeDisposable().add(getDataManager()
            .forecastActivity(date)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response -> {
                if (response != null){
                    if(response.getSuccess() && response.getData() != null) {
                        modelLiveData.setValue(response.getData());
                        if(response.getData().getSuitable() != null && !response.getData().getSuitable().isEmpty()) {
                            modelSuitableLiveData.setValue(response.getData().getSuitable());
                        }
                        if(response.getData().getUnsuitable() != null && !response.getData().getUnsuitable().isEmpty()) {
                            modelUnsuitableLiveData.setValue(response.getData().getUnsuitable());
                        }
                        saveForecastActivityDb(finalDate, response.getData());

                        getNavigator().reset(true);
                    }
                    else {
                        getNavigator().showMessage("CalendarData not found");
                        getNavigator().reset(false);
                    }
                }

            getNavigator().forecastDialog(false);
        }, throwable ->{
            getNavigator().reset(false);
            if(throwable instanceof ConnectException){
                getNavigator().showMessage(R.string.network_error);
            }
            throwable.printStackTrace();
            getNavigator().forecastDialog(false);
        }));
    }


    void forcastActivity(){
//        String date = General.getDateSend();
        String date = "2020-01-01";
        if(getDataManager().isPremium()) {
            forcastMonth(date);
        }

        getNavigator().isPremiumAccount(getDataManager().isPremium());

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
            .forecastActivity(date)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response -> {
                if (response != null){
                    if(response.getSuccess() && response.getData() != null) {
                        modelLiveData.setValue(response.getData());
                        if(response.getData().getSuitable() != null && !response.getData().getSuitable().isEmpty()) {
                            modelSuitableLiveData.setValue(response.getData().getSuitable());
                        }
                        if(response.getData().getUnsuitable() != null && !response.getData().getUnsuitable().isEmpty()) {
                            modelUnsuitableLiveData.setValue(response.getData().getUnsuitable());
                        }
                    }
                }
                setIsLoading(false);
        }, throwable ->{
            throwable.printStackTrace();
            setIsLoading(false);
        }));
    }

    private void forcastMonth(String date){

        String token = "Bearer " + getDataManager().getToken();
//        String token = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjEyYmEwMjE0NGZiYzUzYjgxZWUzMzgwNzVhYTkzYzA5M2NlNzIwMGFiODBiNTViMTEzMGNkYmI4ODhlNzZjMmFlNDhjNDQ4NTRkZjBmM2EwIn0.eyJhdWQiOiIxIiwianRpIjoiMTJiYTAyMTQ0ZmJjNTNiODFlZTMzODA3NWFhOTNjMDkzY2U3MjAwYWI4MGI1NWIxMTMwY2RiYjg4OGU3NmMyYWU0OGM0NDg1NGRmMGYzYTAiLCJpYXQiOjE1NzU3MjIyNjEsIm5iZiI6MTU3NTcyMjI2MSwiZXhwIjoxNjA3MzQ0NjYxLCJzdWIiOiI0Iiwic2NvcGVzIjpbXX0.cs9mq420yPjul1e1fYDVztW8FsrocC7TfrkKRB1f0Ckl9rCX8aQYnA1adlSy9t908tAW8gCSw4AEHc5XgrIGD0Q1VT_gJJomW-rZTPAxF_F9Gv1IXC15IOmNJSED4U_RrTuoZW-KXavukGquOwtW_UDcrKOCmAEOrlYMi1eIGYogAn5LDIvQlnlIqbiqS_TKbXs6yuQcT5xx1SmzvDMB06H4x4wAuSCgaTpQnLX8zdFx0AvLex8fTxWQlUJshGFVxXlVHHmxHAysPtdYQ5wCQ85NUhxbjseQrRCD79IOwqJF21B5LqYXIcRAtrnieBz24CsMKHeRkhm7hWMw0sKzFggAtGP7HraY8w9TeqtrubkzaymlDD2xER2FDImPjdry01Pxa9vSfXK0_HI9GiXnFd1AZVLn-1sVcL5YcHRRMYMY59O2Sv5rkV6RR1IXPkeUoa7trcQ6rh0AQ3On-snA0JWjxGsb0YYgAtuoLXLF-4C1GqURP2DYWKW50Y-XuJPPGrKueGrbLijnkIs4ZpLobqhODPB9PTmK_Q1N223No-j1rXpxDSmgaT6gGPkUx0s480sKGwXgyVU-H3zec0q7bx12rfALN2X-nvCYF_ptFFiRBd_OHHyA6VOV_R2JFqjYNhHZWBPaJzSkL5rJX2b4hkB_bvDiWKGOnui7a8wvdMo";


//        getNavigator().forecastDialog(true);
        getCompositeDisposable().add(getDataManager()
            .forecastMonth(token, date)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response -> {
                if (response != null){
                    if(response.getSuccess() && response.getData() != null) {
                        addMonthModelData(response.getData());
                        saveForecastMonthDb(date, response.getData());
//                        monthModelLiveData.setValue(response.getCalendarData());

                        getNavigator().offlineMode(false);
                        return;
                    }
                }
                getNavigator().offlineMode(true);
//                getNavigator().forecastDialog(false);
        }, throwable ->{
//                getNavigator().forecastDialog(false);
                throwable.printStackTrace();
            if(throwable instanceof ConnectException){
                getNavigator().offlineMode(true);
            }
        }));
    }

    void setGuestUserTitle(String text) {
        animalName.set(text);
    }

    public String getAdsUnitId() {
        return getDataManager().getAdsUnitId();
    }

    void loadGhostMonth() {
        getCompositeDisposable().add(getDataManager()
                .ghostMonth()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null &&
                                response.getData().getSlider() != null) {
                            modelGhostLiveData.setValue(response.getData().getSlider());
                            getNavigator().ghostMonthStatus(response.getData().getGhostMonthStatus() == 1);
                            return;
                        }
                    }
                    getNavigator().ghostMonthStatus(false);
//                getNavigator().forecastDialog(false);
                }, throwable ->{
                    throwable.printStackTrace();
//                getNavigator().forecastDialog(false);
//                    if(throwable instanceof ConnectException){
//                        getNavigator().showMessage(R.string.network_error);
//                    }

                    getNavigator().ghostMonthStatus(false);
                }));
    }

    private void loadFlyStar() {
        getCompositeDisposable().add(getDataManager()
                .flyStar()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null
                                && response.getData().getSlider() != null) {
                            modelFlyLiveData.setValue(response.getData().getSlider());
                            saveFlyStarDb(response.getData().getSlider());
                        }
                    }
//                getNavigator().forecastDialog(false);
                }, throwable ->{
                    throwable.printStackTrace();
//                getNavigator().forecastDialog(false);
//                    if(throwable instanceof ConnectException){
//                        getNavigator().showMessage(R.string.network_error);
//                    }
                }));
    }

    //------------------------------

    private void getForecastActivityDb(String date){
        if(getDataManager().isPremium()) {
            getForecastMonthDb(date);
        }

        getNavigator().isPremiumAccount(getDataManager().isPremium());

        getCompositeDisposable().add(getDataManager()
                .getForecastActivity(date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        modelLiveData.setValue(response);
                        if(response.getSuitable() != null && !response.getSuitable().isEmpty()) {
                            modelSuitableLiveData.setValue(response.getSuitable());
                        }
                        if(response.getUnsuitable() != null && !response.getUnsuitable().isEmpty()) {
                            modelUnsuitableLiveData.setValue(response.getUnsuitable());
                        }
                    }
                    else {
                        forcastActivityApi(date);
                    }

                }, throwable ->{
                    forcastActivityApi(date);
                }));
    }

    private void getForecastMonthDb(String date){
        getCompositeDisposable().add(getDataManager()
                .getForecastMonth(date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        addMonthModelData(response);
                    }
                    else{
                        forcastMonth(date);
                    }
                }, throwable -> {
                    forcastMonth(date);
                    throwable.printStackTrace();
                }));
    }

    void getFlyStarDb(){
        getCompositeDisposable().add(getDataManager()
                .getFlyStar()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()){
                            modelFlyLiveData.setValue(response);
                    }
                    else{
                        loadFlyStar();
                    }
                }, throwable -> {
                    loadFlyStar();
                }));
    }

    private void saveForecastActivityDb(String date, Data data){
        data.setCurrentDate(date);
        getCompositeDisposable().add(getDataManager()
                .saveForecastActivity(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Logger.e("saveForecastActivityDb saved");
                }, throwable ->{
                    Logger.e("saveForecastActivityDb not:" + throwable.getMessage());
                    throwable.printStackTrace();
                }));
    }

    private void saveForecastMonthDb(String date,
                                     com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data data){
        data.setCurrentDate(date);
        getCompositeDisposable().add(getDataManager()
                .saveForecastMonth(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                }, throwable ->{
                    throwable.printStackTrace();
                }));
    }

    private void saveFlyStarDb(List<Slider> data){
        getCompositeDisposable().add(getDataManager()
                .saveFlyStar(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                }, throwable ->{
                    throwable.printStackTrace();
                }));
    }
}

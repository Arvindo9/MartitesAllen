package com.maritesallen.almanac2020.core.dialogs.country;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 29-11-2019
 */
public class CountryViewModel extends BaseViewModel<CountryNavigator> {
    public final ObservableList<Flag> modelObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<Flag>> modelLiveData;

    public CountryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelLiveData = new MutableLiveData<>();
    }


    //List view---------------------------------------

    private ObservableList<Flag> getObservableList() {
        return modelObservableList;
    }

    void addDataToList(List<Flag> list) {
        modelObservableList.clear();
        modelObservableList.addAll(list);
    }

    MutableLiveData<List<Flag>> getLiveData() {
        return modelLiveData;
    }

    private void setLiveData(List<Flag> list){
        modelLiveData.setValue(list);
    }

    void getFlagsDb(){
//        setTmpData();
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getFlag()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()){
                            setLiveData(response);
                    }
                    else{
                        getFlags();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    // show error
                    getFlags();
                    setIsLoading(false);
                }));
    }

    private void getFlags(){
//        setTmpData();
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getFlags()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && !response.getFlags().isEmpty()) {
                            // load flags
                            setLiveData(response.getFlags());
                            saveFlag(response.getFlags());
                        }
                        else{
                            getNavigator().onFlagLoadFailed(response.getMessage());
                        }
                    }
                    else{
                        // show error
                        getNavigator().onFlagLoadFailed(null);
                    }

                    setIsLoading(false);
                }, throwable -> {
                    // show error
                    getNavigator().onFlagLoadFailed(null);
                    setIsLoading(false);
                }));
    }

    private void saveFlag(List<Flag> flags) {
        getCompositeDisposable().add(getDataManager()
                .saveFlag(flags)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful
                    }
                }, Throwable::printStackTrace));
    }


    //Resource---------------------

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }

    public void cancel() {
        setIsLoading(false);
    }

    public int getListPosition() {
        int _id = 0;
        boolean ok = false;
        for(int i = 0; i < modelObservableList.size() && !ok; i++ ){
            if(modelObservableList.get(i).getCountryCode().equals("175")){
                ok = true;
                _id = i;
            }
        }

        return _id;
    }
}

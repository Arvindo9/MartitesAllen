package com.maritesallen.almanac2020.ui.access;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class AccessViewModel extends BaseViewModel {

    public AccessViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getTerms();
        getFlagsDb();
    }

    public void cancel() {
        setIsLoading(false);
    }

    //Terms------------------------------------

    private void getTerms(){
        getCompositeDisposable().add(getDataManager()
                .termsAndPrivacy()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            saveTerms(response.getData().getTerms());
                        }
                    }
                }, Throwable::printStackTrace));
    }

    private void saveTerms(List<Terms> terms) {
        getCompositeDisposable().add(getDataManager()
                .saveTerms(terms)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Saved succeful
                }, Throwable::printStackTrace));
    }

    //Country----------------------------------

    private void getFlagsDb(){
        getCompositeDisposable().add(getDataManager()
                .getFlag()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response == null || response.isEmpty()){
                        getFlags();
                    }
                }, throwable -> getFlags()));
    }

    private void getFlags(){
        getCompositeDisposable().add(getDataManager()
                .getFlags()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && !response.getFlags().isEmpty()) {
                            // load flags
                            saveFlag(response.getFlags());
                        }
                    }
                }, throwable -> {}));
    }

    private void saveFlag(List<Flag> flags) {
        getCompositeDisposable().add(getDataManager()
                .saveFlag(flags)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Saved succeful
                }, Throwable::printStackTrace));
    }



}

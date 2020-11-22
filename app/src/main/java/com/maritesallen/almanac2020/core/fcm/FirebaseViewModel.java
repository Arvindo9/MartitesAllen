package com.maritesallen.almanac2020.core.fcm;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 12-02-2020
 */
public class FirebaseViewModel extends BaseViewModel {

    public FirebaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

/*
    private void pushFcmToken(String token) {
        getCompositeDisposable().add(getDataManager()
                .pushFcmToken(token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        //Saved succeful
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }
    */
}

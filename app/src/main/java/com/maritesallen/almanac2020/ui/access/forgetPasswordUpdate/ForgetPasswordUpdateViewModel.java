package com.maritesallen.almanac2020.ui.access.forgetPasswordUpdate;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.net.ConnectException;

/**
 * Author : Arvindo Mondal
 * Created on 18-12-2019
 */
public class ForgetPasswordUpdateViewModel extends BaseViewModel<ForgetPasswordUpdateNavigator> {

    public ForgetPasswordUpdateViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onShowPasswordClick(){
        getNavigator().onShowPasswordClick();
    }

    public void onSendEmailClick(View view){
        getNavigator().onSendEmailClick(view);
    }


    void doForgetPassword(String email, String password, String otp) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .forgetPasswordUpdate(email, password, otp)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getMessage() != null) {
                            getNavigator().onSuccess(response.getMessage());
                        }
                        else{
                            getNavigator().onError(response.getMessage());
                        }
                    }
                    else{
                        getNavigator().onError(null);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    getNavigator().onError(null);
                    setIsLoading(false);
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }
}

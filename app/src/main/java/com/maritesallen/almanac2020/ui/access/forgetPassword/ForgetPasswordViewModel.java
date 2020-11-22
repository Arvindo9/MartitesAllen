package com.maritesallen.almanac2020.ui.access.forgetPassword;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.net.ConnectException;

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
public class ForgetPasswordViewModel extends BaseViewModel<ForgetPasswordNavigator> {

    public ForgetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onSendEmailClick(View view){getNavigator().onSendEmailClick(view);}


    void doForgetPassword(String email) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .forgetPassword(email)
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

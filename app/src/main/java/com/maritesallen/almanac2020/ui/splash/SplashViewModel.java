package com.maritesallen.almanac2020.ui.splash;

import android.os.Handler;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.util.General;

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
public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    private boolean isAppReady = false;
    private boolean isWaitComplete = false;
    private int taskId = 0;
    private static int SPLASH_TIME_OUT = 3000;

    private final int LOGGED_IN_MODE_LOGGED_OUT = 1;
    private final int LOGGED_IN_MODE_LOGGED_IN = 2;
    private final int LOGGED_IN_MODE_LOGGED_IN_FACEBOOK = 3;
    private final int LOGGED_IN_MODE_LOGGED_IN_GUEST = 4;
    private final int openPlayStore = 10;

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        onStart();
    }

    private void onStart() {
        getAdsId();
        isAppReady = false;
        isWaitComplete = false;

        waitForThreadComplete();

        //comment it
        decideNextScreen();
//        checkAppVersion(currentVersion);
    }

    void decideNextScreen(){
/*        if(getDataManager().getLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_FIRST_TIME.getType()){
            taskId = LOGGED_IN_MODE_FIRST_TIME;
        }
        */
        if(getDataManager().getLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()){
            taskId = LOGGED_IN_MODE_LOGGED_OUT;
        }
        else if(getDataManager().getLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()){
            taskId = LOGGED_IN_MODE_LOGGED_IN;
        }
        else if(getDataManager().getLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType()){
            taskId = LOGGED_IN_MODE_LOGGED_IN_FACEBOOK;
        }
        else if(getDataManager().getLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()){
            taskId = LOGGED_IN_MODE_LOGGED_IN_GUEST;
        }

        isAppReady = true;
        onNextScreenDecided();
    }

    private void onNextScreenDecided(){
        if(isWaitComplete && isAppReady) {
            switch (taskId) {
                case LOGGED_IN_MODE_LOGGED_OUT:
                    getNavigator().openLoginActivity();
                    break;
                case LOGGED_IN_MODE_LOGGED_IN:
                    getNavigator().openDashboard();
                    break;
                case LOGGED_IN_MODE_LOGGED_IN_FACEBOOK:
                    getNavigator().openDashboardFacebook();
                    break;
                case LOGGED_IN_MODE_LOGGED_IN_GUEST:
                    getNavigator().openDashboardGuest();
                    break;
                case openPlayStore:
                    getNavigator().openPlayStore();
                    break;
            }
        }
    }

    private void waitForThreadComplete(){
        new Handler().postDelayed(() -> {
            isWaitComplete = true;
            onNextScreenDecided();
        }, SPLASH_TIME_OUT);
    }

    //Additional task--------------------------------------

    private void getAdsId() {
        getCompositeDisposable().add(getDataManager()
                .getAdsId()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            if(response.getData().getAdsUnitId() != null &&
                                    !response.getData().getAdsUnitId().isEmpty()) {
                                getDataManager().setAdsUnitId(response.getData().getAdsUnitId());
                                getDataManager().setAdsAppId(response.getData().getAdsId());


                                Logger.e("getAdsUnitId: "+ getDataManager().getAdsUnitId());
                                return;
                            }
                        }
                    }

                    getDataManager().setAdsUnitId(BuildConfig.ADS_BANNER_UNIT);
                }, throwable -> {
                    getDataManager().setAdsUnitId(BuildConfig.ADS_BANNER_UNIT);
                }));
    }

    private void getVersion() {
        getCompositeDisposable().add(getDataManager()
                .getVersion()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getAndroidAppVersion()!=null){
                        if(!response.getAndroidAppVersion().isEmpty()){
                            if(!response.getAndroidAppVersion().equals(BuildConfig.VERSION_NAME)) {
                                if(getDataManager().getVersionDate().isEmpty() ||
                                        !getDataManager().getVersionDate().equals(General.getDateSend())) {
                                    taskId = openPlayStore;
                                }
                            }
                        }
                    }
                }, Throwable::printStackTrace));
    }

    void updateVersionDate(){
        getDataManager().setVersionDate(General.getDateSend());
    }

}

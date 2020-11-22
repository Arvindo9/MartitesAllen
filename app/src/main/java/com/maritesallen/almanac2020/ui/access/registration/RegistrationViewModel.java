package com.maritesallen.almanac2020.ui.access.registration;

import com.maritesallen.almanac2020.BuildConfig;
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
public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {

    public RegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    //Resource-------------------

    public void onShowPasswordClick() {
        getNavigator().onShowPasswordClick();
    }

    public void onShowConfirmPasswordClick() {
        getNavigator().onShowConfirmPasswordClick();
    }

    public void onSignUpClick() {
        getNavigator().onSignUpClick();
    }

    public void onDateClick() {
        getNavigator().onDateClick();
    }

    public void onTimeClick() {
        getNavigator().onTimeClick();
    }

    public void onTermsClick() {
        getNavigator().onTermsClick();
    }

    public void onCountryClick() {
        getNavigator().onCountryClick();
    }

    //Task-------------------------

    void doRegistration(String name, String email, String date, String time,
                        String county, String password, String location, String deviceId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .registration(name, email,password, date, time, county, deviceId, BuildConfig.OS_TYPE, location )

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
//                            getDataManager().setToken(response.getCalendarData().getToken());
//                            getDataManager().setUserName(response.getCalendarData().getName());
//                            getDataManager().setBirthDate(date);
//                            getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);

                            getDataManager().setToken(response.getData().getToken());
                            getDataManager().setUserName(response.getData().getName());
                            getDataManager().setBirthDate(response.getData().getBirthDate());
                            getDataManager().setPremium(response.getData().getIsPremium() != 0);
                            getDataManager().setAnimalId(String.valueOf(response.getData().getAnimalId()));
                            getDataManager().setAnimal(String.valueOf(response.getData().getUserAnimalTitle()));
                            getDataManager().setAnimalLink(String.valueOf(response.getData().getUserAnimalImage()));
                            getDataManager().setProfilePic(response.getData().getProfilePic());
                            getDataManager().setUserRegistrationId(response.getData().getRegistrationId());
                            getDataManager().setYearId(response.getData().getYearId());
                            getDataManager().setSubscribeProductId(response.getData().getSubscribeApp());
                            getDataManager().setCountryCode(response.getData().getCountry());

                            getDataManager().setBase64EncodedPublicKey(response.getData().getAppPlaystoreToken());

                            getDataManager().setCompanyIdDrm(response.getData().getCompanyId());
                            getDataManager().setApplicationIdDrm(response.getData().getApplicationId());
                            getDataManager().setPrivateKeyDrm(response.getData().getPrivateKey());
                            getDataManager().setPublicKeyDrm(response.getData().getPublixcKey());
                            getDataManager().setDrmToken(response.getData().getDrm_token());
                            getDataManager().setUserIdDrm(response.getData().getDrm_userId());
                            getDataManager().setProjectIdDrm(response.getData().getDrm_projectId());
                            getDataManager().setProjectNameDrm(response.getData().getDrm_project_name());
                            getDataManager().setDobUpdate(response.getData().getUpdateDob() == 0);

                            getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
                            getNavigator().onRegistrationSuccess();
                        }
                        else{
                            getNavigator().onRegistrationError(response.getMessage());
                        }
                    }
                    else{
                        getNavigator().onRegistrationError(null);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    getNavigator().onRegistrationError(null);
                    setIsLoading(false);
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));

    }

}
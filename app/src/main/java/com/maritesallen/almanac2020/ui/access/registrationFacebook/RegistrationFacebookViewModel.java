package com.maritesallen.almanac2020.ui.access.registrationFacebook;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.net.ConnectException;
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
public class RegistrationFacebookViewModel extends BaseViewModel<RegistrationFacebookNavigation> {

    public RegistrationFacebookViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onDateClick(View view) {
        getNavigator().onDateClick(view);
    }
    public void onTimeClick(View view) {
        getNavigator().onTimeClick(view);
    }
    public void onCountryClick(View view) {
        getNavigator().onCountryClick(view);
    }
    public void onLoginUpClick(View view) {
        getNavigator().onLoginUpClick(view);
    }

    //Task-----------------------------------

    void doLoginWithFacebook(String email, String date, String time, String county) {

        String token = "Bearer " + getDataManager().getToken();
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .facebookLoginSecondary(token, email, date, time, county)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            if(response.getData().getCompletionStatus() == 1) {
                                getDataManager().setToken(response.getData().getToken());
                                getDataManager().setEmail(response.getData().getEmail());
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

                                getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK);
                                savePremiumBooks(response.getData().getBookPremium());
                                getNavigator().openDashboard();
                            }
                        }
                        else{
                            getNavigator().onLoginError(response.getMessage());
                        }
                    }
                    else{
                        getNavigator().onLoginError(null);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    getNavigator().onLoginError(null);
                    throwable.printStackTrace();
                    setIsLoading(false);

                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }

    private void savePremiumBooks(List<BookPremium> bookPremium) {
        getCompositeDisposable().add(getDataManager()
                .saveBookPremium(bookPremium)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful
                    }
                }, Throwable::printStackTrace));
    }
}

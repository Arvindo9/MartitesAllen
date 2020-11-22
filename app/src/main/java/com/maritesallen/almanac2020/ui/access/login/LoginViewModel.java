package com.maritesallen.almanac2020.ui.access.login;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.tasks.Task;

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
public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private final Task task;

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Task task) {
        super(dataManager, schedulerProvider);
        this.task = task;
    }

    //Resource-------------------

    public void onForgetPasswordClick(View view){getNavigator().onForgetPasswordClick(view);}
    public void onShowPasswordClick(){getNavigator().onShowPasswordClick();}
    public void onLoginClick(View view){getNavigator().onLoginClick(view);}
    public void onSignUpClick(View view){getNavigator().onSignUpClick(view);}
    public void onRememberClick(){getNavigator().onRememberClick();}
    public void onTermsClick(){getNavigator().onTermsClick();}

    //Task----------------------

    void doLogin(String email, String password) {
        //TODO Login
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .login(email, password)

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            getDataManager().setToken(response.getData().getToken());
                            getDataManager().setUserName(response.getData().getName());
                            getDataManager().setUserRegistrationId(response.getData().getRegistrationId());
                            getDataManager().setYearId(response.getData().getYearId());
                            getDataManager().setBirthDate(response.getData().getBirthDate());
                            getDataManager().setPremium(response.getData().getIsPremium() != 0);
                            getDataManager().setAnimalId(String.valueOf(response.getData().getAnimalId()));
                            getDataManager().setAnimal(String.valueOf(response.getData().getUserAnimalTitle()));
                            getDataManager().setAnimalLink(String.valueOf(response.getData().getUserAnimalImage()));
                            getDataManager().setProfilePic(response.getData().getProfilePic());
                            getDataManager().setCountryCode(response.getData().getCountry());

                            getDataManager().setSubscribeProductId(response.getData().getSubscribeApp());
                            getDataManager().setBase64EncodedPublicKey(response.getData().getAppPlaystoreToken());

                            getDataManager().setCompanyIdDrm(response.getData().getCompanyId());
                            getDataManager().setApplicationIdDrm(response.getData().getApplicationId());
                            getDataManager().setPrivateKeyDrm(response.getData().getPrivateKey());
                            String sd=response.getData().getPublixcKey();
                            Logger.e("response.getData().getPublixcKey", response.getData().getPublixcKey());
                            getDataManager().setPublicKeyDrm(response.getData().getPublixcKey());
                            getDataManager().setDrmToken(response.getData().getDrm_token());
                            getDataManager().setUserIdDrm(response.getData().getDrm_userId());
                            getDataManager().setProjectIdDrm(response.getData().getDrm_projectId());
                            getDataManager().setProjectNameDrm(response.getData().getDrm_project_name());
                            getDataManager().setDobUpdate(response.getData().getUpdateDob() == 0);


                            getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);

                            if(response.getData().getBookPremium() != null && !response.getData().getBookPremium().isEmpty()){
                                savePremiumBooks(response.getData().getBookPremium());
                            }
                            getNavigator().onLoginSuccess();

//                            if(getDataManager().isPremium()){
//                                task.downloadCalendar();
//                            }
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

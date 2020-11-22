package com.maritesallen.almanac2020.ui.dashboard.profile;

import android.net.Uri;
import android.view.View;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.tasks.Task;
import com.maritesallen.almanac2020.utils.util.General;

import java.io.File;
import java.net.ConnectException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class ProfileViewModel extends BaseViewModel<ProfileNavigator>{

    public final ObservableField<String> userName;
    public final ObservableField<String> birthDate;
    public final ObservableField<String> profilePic;
    public final ObservableField<Uri> profilePicFile;

    public final ObservableField<String> yearOfAnimal;
    public final ObservableField<String> yearList;
    public final ObservableField<String> description;
    public final ObservableField<String> romanceIcon;
    public final ObservableField<String> romanceTitle;
    public final ObservableField<String> romance;
    public final ObservableField<String> wealthIcon;
    public final ObservableField<String> wealthTitle;
    public final ObservableField<String> wealth;
    public final ObservableField<String> healthIcon;
    public final ObservableField<String> health;
    public final ObservableField<String> healthTitle;
    public final ObservableField<String> careerIcon;
    public final ObservableField<String> careerTitle;
    public final ObservableField<String> career;
    public final ObservableField<String> animalIcon;

    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Task task) {
        super(dataManager, schedulerProvider);
        profilePicFile = new ObservableField<>();

        Logger.e("Pic " + getDataManager().getProfilePic());
        profilePic = new ObservableField<>(getDataManager().getProfilePic());
        userName = new ObservableField<>(getDataManager().getUserName());
//        birthDate = new ObservableField<>(getDataManager().getBirthDate());
        birthDate = new ObservableField<>(General.getDateReadable1(getDataManager().getBirthDate()));

        yearOfAnimal = new ObservableField<>();
        yearList = new ObservableField<>();
        description = new ObservableField<>();
        romanceIcon = new ObservableField<>();
        romanceTitle = new ObservableField<>();
        romance = new ObservableField<>();
        wealthIcon = new ObservableField<>();
        wealthTitle = new ObservableField<>();
        wealth = new ObservableField<>();
        healthIcon = new ObservableField<>();
        health = new ObservableField<>();
        healthTitle = new ObservableField<>();
        careerIcon = new ObservableField<>();
        careerTitle = new ObservableField<>();
        career = new ObservableField<>();
        animalIcon = new ObservableField<>();

        task.loadTerms();
    }

    //Resource-----------------------

    public void onUpgradeClick(View view){
        getNavigator().onUpgradeClick(view);
    }
    public void onRestoreClick(View view){
        getNavigator().onRestoreClick(view);
    }
    public void onTermsClick(View view){
        getNavigator().onTermsClick(view);
    }
    public void onPrivacyClick(View view){
        getNavigator().onPrivacyClick(view);
    }
    public void onEditProfileCancelClick(){getNavigator().onEditProfileCancelClick();}
    public void onEditProfileSaveClick(){getNavigator().onEditProfileSaveClick();}
    public void onEditProfileClick(){getNavigator().onEditProfileClick();}
    public void onChangeProfileImageClick(){getNavigator().onChangeProfileImageClick();}
    public void onAppInfoClick(View view){getNavigator().onAppInfoClick(view);}
    public void onAboutClick(View view){getNavigator().onAboutClick(view);}
    public void onDateClick(View view){getNavigator().onDateClick(view);}

    private void setProfileData(){
        userName.set(getDataManager().getUserName());
        birthDate.set(getDataManager().getBirthDate());
    }

    // Task--------------------------

    void setProfilePicFile(Uri uri){
        profilePicFile.set(uri);
    }

    void setProfilePicReset(){
        profilePic.set(getDataManager().getProfilePic());
    }

    String getProfilePic(){
        return profilePic.get();
    }

    boolean isPremium(){
        return getDataManager().isPremium();
    }

    boolean isDobUpdate(){
        return getDataManager().isDobUpdate();
    }

    void updateProfile(File file, String name, String birthDate) {
        setIsLoading(true);

        String token = "Bearer " + getDataManager().getToken();

        if(file != null) {
            Logger.e("File size:" + file.length());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            // add another part within the multipart request
            RequestBody fullName =
                    RequestBody.create(MediaType.parse("multipart/form-data"), name);
            RequestBody date =
                    RequestBody.create(MediaType.parse("multipart/form-data"), birthDate);

            getCompositeDisposable().add(getDataManager()
                    .mediaUploadApi(token, fullName, date, body)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getData() != null) {
//                            getDataManager().setUserName(response.getCalendarData().getName());
//                            getDataManager().setBirthDate(response.getCalendarData().getBirthDate());
//                            getDataManager().setProfilePic(response.getCalendarData().getProfilePic());
//                            profilePic.set(response.getCalendarData().getProfilePic());

                            getDataManager().setToken(response.getData().getToken());
                            getDataManager().setUserName(response.getData().getName());
                            getDataManager().setBirthDate(response.getData().getBirthDate());
                            getDataManager().setPremium(response.getData().getIsPremium() != 0);
                            getDataManager().setAnimalId(String.valueOf(response.getData().getAnimalId()));
                            getDataManager().setAnimal(String.valueOf(response.getData().getUserAnimalTitle()));
                            getDataManager().setAnimalLink(String.valueOf(response.getData().getUserAnimalImage()));
                            getDataManager().setProfilePic(response.getData().getProfilePic());
                            getDataManager().setDobUpdate(response.getData().getUpdateDob() == 0);
                            profilePic.set(response.getData().getProfilePic());

                            loadProfileData();
                        }
                        else {
                            setProfileData();
                        }
                        setIsLoading(false);
                        getNavigator().editCompleted();
                    }, throwable -> {
                        setProfileData();
                        throwable.printStackTrace();
                        setIsLoading(false);
                        getNavigator().editCompleted();
                        if(throwable instanceof ConnectException){
                            getNavigator().showMessage(R.string.network_error);
                        }
                    }));

        }
        else{
            getCompositeDisposable().add(getDataManager()
                    .mediaUploadApiEmpty(token, name, birthDate)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getData() != null) {
//                            getDataManager().setUserName(response.getCalendarData().getName());
//                            getDataManager().setBirthDate(response.getCalendarData().getBirthDate());
//                            getDataManager().setProfilePic(response.getCalendarData().getProfilePic());

                            getDataManager().setToken(response.getData().getToken());
                            getDataManager().setUserName(response.getData().getName());
                            getDataManager().setBirthDate(response.getData().getBirthDate());
                            getDataManager().setPremium(response.getData().getIsPremium() != 0);
                            getDataManager().setAnimalId(String.valueOf(response.getData().getAnimalId()));
                            getDataManager().setAnimal(String.valueOf(response.getData().getUserAnimalTitle()));
                            getDataManager().setAnimalLink(String.valueOf(response.getData().getUserAnimalImage()));
                            getDataManager().setProfilePic(response.getData().getProfilePic());
                            getDataManager().setDobUpdate(response.getData().getUpdateDob() == 0);
                            profilePic.set(response.getData().getProfilePic());

                            loadProfileData();
                        }
                        else {
                            setProfileData();
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        setProfileData();
                        throwable.printStackTrace();
                        setIsLoading(false);
                        if(throwable instanceof ConnectException){
                            getNavigator().showMessage(R.string.network_error);
                        }
                    }));
        }
    }

    //Profile data---------------

    void loadProfileDataDb(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getProfile()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        animalIcon.set(response.getAnimal().getImage());
                        yearOfAnimal.set("Year of the " + response.getAnimal().getTitle());
                        yearList.set(response.getAnimal().getYears());
                        description.set(response.getAnimal().getDescription());

                        romanceIcon.set(response.getRomance().getImage());
                        romanceTitle.set(response.getRomance().getKey());
                        romance.set(response.getRomance().getValue());

                        wealthIcon.set(response.getWealth().getImage());
                        wealthTitle.set(response.getWealth().getKey());
                        wealth.set(response.getWealth().getValue());

                        healthIcon.set(response.getHealth().getImage());
                        healthTitle.set(response.getHealth().getKey());
                        health.set(response.getHealth().getValue());

                        careerIcon.set(response.getCareer().getImage());
                        careerTitle.set(response.getCareer().getKey());
                        career.set(response.getCareer().getValue());

//                        int rand = (int)(Math.random() * 2);
//                        if(rand == 1){
//                            loadProfileData();
//                        }
                    }
                    else {
//                        loadProfileData();
                    }

                    loadProfileData();
                    setIsLoading(false);
                }, throwable ->{
                    loadProfileData();
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    private void loadProfileData(){
        String yearId = getDataManager().getYearId();
        String animalId = getDataManager().getAnimalId();

        getCompositeDisposable().add(getDataManager()
                .profileData(animalId, yearId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            if (response.getData().getForecast() != null) {
                                animalIcon.set(response.getData().getAnimal().getImage());
                                yearOfAnimal.set("Year of the " + response.getData().getAnimal().getTitle());
                                yearList.set(response.getData().getAnimal().getYears());
                                description.set(response.getData().getAnimal().getDescription());

                                romanceIcon.set(response.getData().getForecast().getRomance().getImage());
                                romanceTitle.set(response.getData().getForecast().getRomance().getKey());
                                romance.set(response.getData().getForecast().getRomance().getValue());

                                wealthIcon.set(response.getData().getForecast().getWealth().getImage());
                                wealthTitle.set(response.getData().getForecast().getWealth().getKey());
                                wealth.set(response.getData().getForecast().getWealth().getValue());

                                healthIcon.set(response.getData().getForecast().getHealth().getImage());
                                healthTitle.set(response.getData().getForecast().getHealth().getKey());
                                health.set(response.getData().getForecast().getHealth().getValue());

                                careerIcon.set(response.getData().getForecast().getCareer().getImage());
                                careerTitle.set(response.getData().getForecast().getCareer().getKey());
                                career.set(response.getData().getForecast().getCareer().getValue());

                                response.getData().getForecast().setAnimal(response.getData().getAnimal());
                                saveProfile(response.getData().getForecast());
                                getNavigator().offlineMode(false);
                                return;
                            }
                        }
                    }
                    getNavigator().offlineMode(true);
                }, throwable ->{
                    getNavigator().offlineMode(true);
                    throwable.printStackTrace();
//                    if(throwable instanceof ConnectException){
//                        getNavigator().showMessage(R.string.network_error);
//                    }
                }));
    }

    private void saveProfile(Forecast bookPremium) {
        getCompositeDisposable().add(getDataManager()
                .saveProfile(bookPremium)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful
                    }
                }, Throwable::printStackTrace));
    }

    //Profile data---------------?End

    public String getAdsUnitId() {
        return getDataManager().getAdsUnitId();
    }
}

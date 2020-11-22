package com.maritesallen.almanac2020.data.local.prefs;


import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public interface PreferencesService {

    int getLoggedInMode();

    void setLoggedInMode(DataManager.LoggedInMode mode);

    void setUserId(String id);

    String getUserId();

    void setUserRegistrationId(String id);

    String getUserRegistrationId();

    void setUserName(String data);

    String getUserName();

    void setEmail(String email);

    String getEmail();

    void setCountryCode(String countryCode);

    String getCountryCode();

    void setMobile(String mobile);

    String getMobile();

    void setBirthDate(String birthDate);

    String getBirthDate();

    void setToken(String token);

    String getToken();

    void setPremium(boolean data);

    boolean isPremium();

    void setAnimalId(String data);

    String getAnimalId();

    void setAnimal(String data);

    String getAnimal();

    void setAnimalLink(String data);

    String getAnimalLink();

    void setYearId(String data);

    String getYearId();

    void setProfilePic(String data);

    String getProfilePic();

    void setSubscribeProductId(String data);

    String getSubscribeProductId();

    //keys-----------------------

    void setBase64EncodedPublicKey(String data);

    String getBase64EncodedPublicKey();

    void setDrmToken(String data);

    String getDrmToken();

    void setCompanyIdDrm(String data);

    String getCompanyIdDrm();

    void setApplicationIdDrm(String data);

    String getApplicationIdDrm();

    void setPrivateKeyDrm(String data);

    String getPrivateKeyDrm();

    void setPublicKeyDrm(String data);

    String getPublicKeyDrm();

    void setProjectIdDrm(String data);

    String getProjectIdDrm();

    void setUserIdDrm(String data);

    String getUserIdDrm();

    void setVersionDrm(String data);

    String getVersionDrm();

    void setProjectNameDrm(String data);

    String getProjectNameDrm();

    void setDrmIdDrm(String data);

    String getDrmIdDrm();

    void setDobUpdate(boolean data);

    boolean isDobUpdate();




    void setAdsAppId(String data);

    String getAdsAppId();

    void setAdsUnitId(String data);

    String getAdsUnitId();

    void setVersionDate(String data);

    String getVersionDate();

    //Payment sync------------------

    void setPaymentSync(boolean status);

    boolean isPaymentSync();

    void setPurchase(Purchase purchase);

    Purchase getPurchase();

    //fb token--------------

    void setFbToken(String data);

    String getFbToken();

}

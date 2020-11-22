package com.maritesallen.almanac2020.data.local.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.local.db.utils.DataConverter;
import com.maritesallen.almanac2020.data.model.apis.profile.Romance;
import com.maritesallen.almanac2020.di.annotation.PreferenceInfo;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public class AppPreferences implements PreferencesService {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String KEY_USER_ID = "PREF_KEY_USER_ID";
    private static final String KEY_USER_REG_ID = "PREF_KEY_USER_REG_ID";
    private static final String KEY_USER_NAME = "PREF_KEY_USER_NAME";
    private static final String KEY_EMAIL = "PREF_KEY_EMAIL";
    private static final String KEY_COUNTRY_CODE = "PREF_KEY_COUNTRY_CODE";
    private static final String KEY_MOBILE = "PREF_KEY_MOBILE";
    private static final String KEY_TOKEN = "PREF_KEY_TOKEN";
    private static final String KEY_BIRTH_DATE = "PREF_KEY_BIRTH_DATE";
    private static final String KEY_ANIMAL_ID = "PREF_KEY_ANIMAL_ID";
    private static final String KEY_PREMIUM = "PREF_KEY_PREMIUM";
    private static final String KEY_ANIMAL = "PREF_KEY_ANIMAL";
    private static final String KEY_ANIMAL_LINK = "PREF_KEY_ANIMAL_LINK";
    private static final String KEY_YEAR_ID = "PREF_KEY_YEAR_ID";
    private static final String KEY_PEOFILE_PIC = "PREF_KEY_PEOFILE_PIC";
    private static final String KEY_SUBSCRIPTION = "PREF_KEY_SUBSCRIPTION";

    private static final String KEY_Base64EncodedPublicKey = "PREF_KEY_Base64EncodedPublicKey";
    private static final String KEY_DrmToken = "PREF_KEY_DrmToken";
    private static final String KEY_CompanyIdDrm = "PREF_KEY_CompanyIdDrm";
    private static final String KEY_ApplicationIdDrm = "PREF_KEY_ApplicationIdDrm";
    private static final String KEY_PrivateKeyDrm = "PREF_KEY_PrivateKeyDrm";
    private static final String KEY_PublicKeyDrm = "PREF_KEY_PublicKeyDrm";
    private static final String KEY_ProjectIdDrm = "PREF_KEY_ProjectIdDrm";
    private static final String KEY_UserIdDrm = "PREF_KEY_UserIdDrm";
    private static final String KEY_VersionDrm = "PREF_KEY_VersionDrm";
    private static final String KEY_ProjectNameDrm = "PREF_KEY_ProjectNameDrm";
    private static final String KEY_DrmIdDrm = "PREF_KEY_DrmIdDrm";
    private static final String KEY_IsDob = "PREF_KEY_IsDob";
    private static final String KEY_Ads_Id = "KEY_Ads_Id";
    private static final String KEY_Ads_unit = "KEY_Ads_unit";
    private static final String KEY_DateV = "KEY_DateV";
    private static final String KEY_Country = "KEY_Country";

    private static final String KEY_PREMIUM_SYNC = "KEY_PREMIUM_SYNC";
    private static final String KEY_PURCHASE = "KEY_PURCHASE";
    private static final String KEY_FIREBASE_TOKEN = "KEY_FIREBASE_TOKEN";

    private final SharedPreferences pref;

    @Inject
    public AppPreferences(Context context, @PreferenceInfo String prefFileName) {
        pref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getLoggedInMode() {
        return pref.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setLoggedInMode(DataManager.LoggedInMode mode) {
        pref.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public void setUserId(String id) {
        pref.edit().putString(KEY_USER_ID, id).apply();
    }

    @Override
    public String getUserId() {
        return pref.getString(KEY_USER_ID, "");
    }

    @Override
    public void setUserRegistrationId(String id) {
        pref.edit().putString(KEY_USER_REG_ID, id).apply();
    }

    @Override
    public String getUserRegistrationId() {
        return pref.getString(KEY_USER_REG_ID, "");
    }

    @Override
    public void setUserName(String data) {
        pref.edit().putString(KEY_USER_NAME, data).apply();
    }

    @Override
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    @Override
    public void setEmail(String email) {
        pref.edit().putString(KEY_EMAIL, email).apply();
    }

    @Override
    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    @Override
    public void setCountryCode(String countryCode) {
        pref.edit().putString(KEY_COUNTRY_CODE, countryCode).apply();
    }

    @Override
    public String getCountryCode() {
        return pref.getString(KEY_COUNTRY_CODE, "");
    }

    @Override
    public void setMobile(String mobile) {
        pref.edit().putString(KEY_MOBILE, mobile).apply();
    }

    @Override
    public String getMobile() {
        return pref.getString(KEY_MOBILE, "");
    }

    @Override
    public void setBirthDate(String birthDate) {
        pref.edit().putString(KEY_BIRTH_DATE, birthDate).apply();
    }

    @Override
    public String getBirthDate() {
        return pref.getString(KEY_BIRTH_DATE, "");
    }

    @Override
    public void setToken(String token) {
        pref.edit().putString(KEY_TOKEN, token).apply();
    }

    @Override
    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    @Override
    public void setPremium(boolean data) {
        pref.edit().putBoolean(KEY_PREMIUM, data).apply();
    }

    @Override
    public boolean isPremium() {
        return pref.getBoolean(KEY_PREMIUM, false);
    }

    @Override
    public void setAnimalId(String data) {
        pref.edit().putString(KEY_ANIMAL_ID, data).apply();
    }

    @Override
    public String getAnimalId() {
        return pref.getString(KEY_ANIMAL_ID, "");
    }

    @Override
    public void setAnimal(String data) {
        pref.edit().putString(KEY_ANIMAL, data).apply();
    }

    @Override
    public String getAnimal() {
        return pref.getString(KEY_ANIMAL, "");
    }

    @Override
    public void setAnimalLink(String data) {
        pref.edit().putString(KEY_ANIMAL_LINK, data).apply();
    }

    @Override
    public String getAnimalLink() {
        return pref.getString(KEY_ANIMAL_LINK, "");
    }

    @Override
    public void setYearId(String data) {
        pref.edit().putString(KEY_YEAR_ID, data).apply();
    }

    @Override
    public String getYearId() {
        return pref.getString(KEY_YEAR_ID, "1");
    }

    @Override
    public void setProfilePic(String data) {
        pref.edit().putString(KEY_PEOFILE_PIC, data).apply();
    }

    @Override
    public String getProfilePic() {
        return pref.getString(KEY_PEOFILE_PIC, "1");
    }

    @Override
    public void setSubscribeProductId(String data) {
        pref.edit().putString(KEY_SUBSCRIPTION, data).apply();
    }

    @Override
    public String getSubscribeProductId() {
        return pref.getString(KEY_SUBSCRIPTION, "");
    }

    //--------------------------------------

    @Override
    public void setBase64EncodedPublicKey(String data) {
        pref.edit().putString(KEY_Base64EncodedPublicKey, data).apply();
    }

    @Override
    public String getBase64EncodedPublicKey() {
        return pref.getString(KEY_Base64EncodedPublicKey, "");
    }

    @Override
    public void setDrmToken(String data) {
        pref.edit().putString(KEY_DrmToken, data).apply();
    }

    @Override
    public String getDrmToken() {
        return pref.getString(KEY_DrmToken, "");
    }

    @Override
    public void setCompanyIdDrm(String data) {
        pref.edit().putString(KEY_CompanyIdDrm, data).apply();
    }

    @Override
    public String getCompanyIdDrm() {
        return pref.getString(KEY_CompanyIdDrm, "");
    }

    @Override
    public void setApplicationIdDrm(String data) {
        pref.edit().putString(KEY_ApplicationIdDrm, data).apply();
    }

    @Override
    public String getApplicationIdDrm() {
        return pref.getString(KEY_ApplicationIdDrm, "");
    }

    @Override
    public void setPrivateKeyDrm(String data) {
        pref.edit().putString(KEY_PrivateKeyDrm, data).apply();
    }

    @Override
    public String getPrivateKeyDrm() {
        return pref.getString(KEY_PrivateKeyDrm, "");
    }

    @Override
    public void setPublicKeyDrm(String data) {
        pref.edit().putString(KEY_PublicKeyDrm, data).apply();
    }

    @Override
    public String getPublicKeyDrm() {
        return pref.getString(KEY_PublicKeyDrm, "");
    }

    @Override
    public void setProjectIdDrm(String data) {
        pref.edit().putString(KEY_ProjectIdDrm, data).apply();
    }

    @Override
    public String getProjectIdDrm() {
        return pref.getString(KEY_ProjectIdDrm, "");
    }

    @Override
    public void setUserIdDrm(String data) {
        pref.edit().putString(KEY_UserIdDrm, data).apply();
    }

    @Override
    public String getUserIdDrm() {
        return pref.getString(KEY_UserIdDrm, "");
    }

    @Override
    public void setVersionDrm(String data) {
        pref.edit().putString(KEY_VersionDrm, data).apply();
    }

    @Override
    public String getVersionDrm() {
        return pref.getString(KEY_VersionDrm, "");
    }

    @Override
    public void setProjectNameDrm(String data) {
        pref.edit().putString(KEY_ProjectNameDrm, data).apply();
    }

    @Override
    public String getProjectNameDrm() {
        return pref.getString(KEY_ProjectNameDrm, "");
    }

    @Override
    public void setDrmIdDrm(String data) {
        pref.edit().putString(KEY_DrmIdDrm, data).apply();
    }

    @Override
    public String getDrmIdDrm() {
        return pref.getString(KEY_DrmIdDrm, "");
    }

    @Override
    public void setDobUpdate(boolean data) {
        pref.edit().putBoolean(KEY_IsDob, data).apply();
    }

    @Override
    public boolean isDobUpdate() {
        return pref.getBoolean(KEY_IsDob, false);
    }

    @Override
    public void setAdsAppId(String data) {
        pref.edit().putString(KEY_Ads_Id, data).apply();
    }

    @Override
    public String getAdsAppId() {
        return pref.getString(KEY_Ads_Id, "");
    }

    @Override
    public void setAdsUnitId(String data) {
        pref.edit().putString(KEY_Ads_unit, data).apply();
    }

    @Override
    public String getAdsUnitId() {
        return pref.getString(KEY_Ads_unit, "");
    }

    @Override
    public void setVersionDate(String data) {
        pref.edit().putString(KEY_DateV, data).apply();
    }

    @Override
    public String getVersionDate() {
        return pref.getString(KEY_DateV, "");
    }

    @Override
    public void setPaymentSync(boolean status) {
        pref.edit().putBoolean(KEY_PREMIUM_SYNC, status).apply();
        if(status){
            pref.edit().putString(KEY_PURCHASE, "").apply();
        }
    }

    @Override
    public boolean isPaymentSync() {
        return pref.getBoolean(KEY_PREMIUM_SYNC, true);
    }

    @Override
    public void setPurchase(Purchase purchase) {
        String data = DataConverter.getGson().toJson(purchase);
        pref.edit().putString(KEY_PURCHASE, data).apply();
    }

    @Override
    public Purchase getPurchase() {
        String data = pref.getString(KEY_PURCHASE, "");
        if (data.isEmpty()) {
            setPaymentSync(true);
            pref.edit().putString(KEY_PURCHASE, data).apply();
        }
        Type listType = new TypeToken<Purchase>() {}.getType();
        return DataConverter.getGson().fromJson(data, listType);
    }

    @Override
    public void setFbToken(String data) {
        pref.edit().putString(KEY_FIREBASE_TOKEN, data).apply();
    }

    @Override
    public String getFbToken() {
        return pref.getString(KEY_FIREBASE_TOKEN, "");
    }
}

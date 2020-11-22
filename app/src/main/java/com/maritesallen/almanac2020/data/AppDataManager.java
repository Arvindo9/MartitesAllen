package com.maritesallen.almanac2020.data;

import android.content.Context;

import com.google.gson.Gson;
import com.maritesallen.almanac2020.data.model.apis.fb.FcmResponse;
import com.maritesallen.almanac2020.data.local.db.DatabaseService;
import com.maritesallen.almanac2020.data.local.prefs.PreferencesService;
import com.maritesallen.almanac2020.data.model.apis.access.AccessResponse;
import com.maritesallen.almanac2020.data.model.apis.ads.AdsRespose;
import com.maritesallen.almanac2020.data.model.apis.books.BookResponse;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.CalendarResponse;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.flag.FlagResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastActivityResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalendarResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.ForecastMonthResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.ForecastSliderRespondse;
import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;
import com.maritesallen.almanac2020.data.model.apis.profile.ProfileResponse;
import com.maritesallen.almanac2020.data.model.apis.purchase.PurchaseResponse;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.data.model.apis.slider.SliderResponse;
import com.maritesallen.almanac2020.data.model.apis.terms.TermsResponse;
import com.maritesallen.almanac2020.data.model.apis.version.VersionResponse;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;
import com.maritesallen.almanac2020.data.remote.APIService;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Author       : Arvindo Mondal
 * Created on   : 09-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
@Singleton
public class AppDataManager implements DataManager {

    private final APIService apiService;
    private final Context context;
    private final DatabaseService dbService;
    private final Gson gson;
    private final PreferencesService pref;

    @Inject
    AppDataManager(Context context, PreferencesService preferencesService,
                   APIService apiService, DatabaseService dbService, Gson gson) {
        this.context = context;
        this.pref = preferencesService;
        this.dbService = dbService;
        this.gson = gson;
        this.apiService = apiService;
    }

    //Preferences----------------------------
    @Override
    public int getLoggedInMode() {
        return pref.getLoggedInMode();
    }

    @Override
    public void setLoggedInMode(LoggedInMode mode) {
        pref.setLoggedInMode(mode);
    }

    @Override
    public void setUserId(String id) {
        pref.setUserId(id);
    }

    @Override
    public String getUserId() {
        return pref.getUserId();
    }

    @Override
    public void setUserRegistrationId(String id) {
        pref.setUserRegistrationId(id);
    }

    @Override
    public String getUserRegistrationId() {
        return pref.getUserRegistrationId();
    }

    @Override
    public void setUserName(String data) {
        pref.setUserName(data);
    }

    @Override
    public String getUserName() {
        return pref.getUserName();
    }

    @Override
    public void setEmail(String email) {
        pref.setEmail(email);
    }

    @Override
    public String getEmail() {
        return pref.getEmail();
    }

    @Override
    public void setCountryCode(String countryCode) {
        pref.setCountryCode(countryCode);
    }

    @Override
    public String getCountryCode() {
        return pref.getCountryCode();
    }

    @Override
    public void setMobile(String mobile) {
        pref.setMobile(mobile);
    }

    @Override
    public String getMobile() {
        return pref.getMobile();
    }

    @Override
    public void setBirthDate(String birthDate) {
        pref.setBirthDate(birthDate);
    }

    @Override
    public String getBirthDate() {
        return pref.getBirthDate();
    }

    @Override
    public void setToken(String token) {
        pref.setToken(token);
    }

    @Override
    public String getToken() {
        return pref.getToken();
    }

    @Override
    public void setPremium(boolean data) {
        pref.setPremium(data);
    }

    @Override
    public boolean isPremium() {
        return pref.isPremium();
    }

    @Override
    public void setAnimalId(String data) {
        pref.setAnimalId(data);
    }

    @Override
    public String getAnimalId() {
        return pref.getAnimalId();
    }

    @Override
    public void setAnimal(String data) {
        pref.setAnimal(data);
    }

    @Override
    public String getAnimal() {
        return pref.getAnimal();
    }

    @Override
    public void setAnimalLink(String data) {
        pref.setAnimalLink(data);
    }

    @Override
    public String getAnimalLink() {
        return pref.getAnimalLink();
    }

    @Override
    public void setYearId(String data) {
        pref.setYearId(data);
    }

    @Override
    public String getYearId() {
        return pref.getYearId();
    }

    @Override
    public void setProfilePic(String data) {
        pref.setProfilePic(data);
    }

    @Override
    public String getProfilePic() {
        return pref.getProfilePic();
    }

    @Override
    public void setSubscribeProductId(String data) {
        pref.setSubscribeProductId(data);
    }

    @Override
    public String getSubscribeProductId() {
        return pref.getSubscribeProductId();
    }

    @Override
    public void setBase64EncodedPublicKey(String data) {
        pref.setBase64EncodedPublicKey(data);
    }

    @Override
    public String getBase64EncodedPublicKey() {
        return pref.getBase64EncodedPublicKey();
    }

    @Override
    public void setDrmToken(String data) {
        pref.setDrmToken(data);
    }

    @Override
    public String getDrmToken() {
        return pref.getDrmToken();
    }

    @Override
    public void setCompanyIdDrm(String data) {
        pref.setCompanyIdDrm(data);
    }

    @Override
    public String getCompanyIdDrm() {
        return pref.getCompanyIdDrm();
    }

    @Override
    public void setApplicationIdDrm(String data) {
        pref.setApplicationIdDrm(data);
    }

    @Override
    public String getApplicationIdDrm() {
        return pref.getApplicationIdDrm();
    }

    @Override
    public void setPrivateKeyDrm(String data) {
        pref.setPrivateKeyDrm(data);
    }

    @Override
    public String getPrivateKeyDrm() {
        return pref.getPrivateKeyDrm();
    }

    @Override
    public void setPublicKeyDrm(String data) {
        pref.setPublicKeyDrm(data);
    }

    @Override
    public String getPublicKeyDrm() {
        return pref.getPublicKeyDrm();
    }

    @Override
    public void setProjectIdDrm(String data) {
        pref.setProjectIdDrm(data);
    }

    @Override
    public String getProjectIdDrm() {
        return pref.getProjectIdDrm();
    }

    @Override
    public void setUserIdDrm(String data) {
        pref.setUserIdDrm(data);
    }

    @Override
    public String getUserIdDrm() {
        return pref.getUserIdDrm();
    }

    @Override
    public void setVersionDrm(String data) {
        pref.setVersionDrm(data);
    }

    @Override
    public String getVersionDrm() {
        return pref.getVersionDrm();
    }

    @Override
    public void setProjectNameDrm(String data) {
        pref.setProjectNameDrm(data);
    }

    @Override
    public String getProjectNameDrm() {
        return pref.getProjectNameDrm();
    }

    @Override
    public void setDrmIdDrm(String data) {
        pref.setDrmIdDrm(data);
    }

    @Override
    public String getDrmIdDrm() {
        return pref.getDrmIdDrm();
    }

    @Override
    public void setDobUpdate(boolean data) {
        pref.setDobUpdate(data);
    }

    @Override
    public boolean isDobUpdate() {
        return pref.isDobUpdate();
    }

    @Override
    public void setAdsAppId(String data) {
        pref.setAdsAppId(data);
    }

    @Override
    public String getAdsAppId() {
        return pref.getAdsAppId();
    }

    @Override
    public void setAdsUnitId(String data) {
        pref.setAdsUnitId(data);
    }

    @Override
    public String getAdsUnitId() {
        return pref.getAdsUnitId();
    }

    @Override
    public void setVersionDate(String data) {
        pref.setVersionDate(data);
    }

    @Override
    public String getVersionDate() {
        return pref.getVersionDate();
    }

    @Override
    public void setPaymentSync(boolean status) {
        pref.setPaymentSync(status);
    }

    @Override
    public boolean isPaymentSync() {
        return pref.isPaymentSync();
    }

    @Override
    public void setPurchase(Purchase purchase) {
        pref.setPurchase(purchase);
    }

    @Override
    public Purchase getPurchase() {
        return pref.getPurchase();
    }

    @Override
    public void setFbToken(String data) {
        pref.setFbToken(data);
    }

    @Override
    public String getFbToken() {
        return pref.getFbToken();
    }

    //Database---------------------------------

    @Override
    public Flowable<Boolean> saveTerms(List<Terms> list) {
        return dbService.saveTerms(list);
    }

    @Override
    public Flowable<List<Terms>> getTerms() {
        return dbService.getTerms();
    }

    @Override
    public Flowable<Boolean> saveBookPremium(List<BookPremium> list) {
        return dbService.saveBookPremium(list);
    }

    @Override
    public Flowable<List<BookPremium>> getBookPremium() {
        return dbService.getBookPremium();
    }

    @Override
    public Flowable<Boolean> saveBooks(List<Books> list) {
        return dbService.saveBooks(list);
    }

    @Override
    public Flowable<List<Books>> getBooks() {
        return dbService.getBooks();
    }

    @Override
    public Flowable<Boolean> isThisBooksPurchase(int bookId) {
        return dbService.isThisBooksPurchase(bookId);
    }

    @Override
    public Flowable<Boolean> deleteBookPremium() {
        return dbService.deleteBookPremium();
    }

    @Override
    public Flowable<Boolean> deleteCalendar() {
        return dbService.deleteCalendar();
    }

    @Override
    public Flowable<Boolean> saveCalendar(List<CalendarModel> list) {
        return dbService.saveCalendar(list);
    }

    @Override
    public Flowable<List<CalendarModel>> getCalendar() {
        return dbService.getCalendar();
    }

    @Override
    public Flowable<List<CalendarModel>> getCalendar(int monthId) {
        return dbService.getCalendar(monthId);
    }

    @Override
    public Flowable<Boolean> saveProfile(Forecast data) {
        return dbService.saveProfile(data);
    }

    @Override
    public Flowable<Forecast> getProfile() {
        return dbService.getProfile();
    }

    @Override
    public Flowable<Boolean> deleteProfile() {
        return dbService.deleteProfile();
    }

    @Override
    public Flowable<Boolean> saveFlag(List<Flag> data) {
        return dbService.saveFlag(data);
    }

    @Override
    public Flowable<List<Flag>> getFlag() {
        return dbService.getFlag();
    }

    @Override
    public Flowable<Boolean> saveForecastActivity(Data data) {
        return dbService.saveForecastActivity(data);
    }

    @Override
    public Flowable<Boolean> saveForecastMonth(com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data data) {
        return dbService.saveForecastMonth(data);
    }

    @Override
    public Flowable<Boolean> saveFlyStar(List<Slider> data) {
        return dbService.saveFlyStar(data);
    }

    @Override
    public Flowable<Data> getForecastActivity(String date) {
        return dbService.getForecastActivity(date);
    }

    @Override
    public Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> getForecastMonth(String date) {
        return dbService.getForecastMonth(date);
    }

    @Override
    public Flowable<List<Slider>> getFlyStar() {
        return dbService.getFlyStar();
    }

    @Override
    public Flowable<Boolean> saveForecastCalendar(com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data data) {
        return dbService.saveForecastCalendar(data);
    }

    @Override
    public Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data> getForecastCalendar(
            String iconId, String dataType) {
        return dbService.getForecastCalendar(iconId, dataType);
    }

    @Override
    public Flowable<Boolean> clearAllDb() {
        return dbService.clearAllDb();
    }

    //APIs-------------------------------------

    @Override
    public Flowable<FlagResponse> getFlags() {
        return apiService.getFlags();
    }

    @Override
    public Flowable<VersionResponse> getVersion() {
        return apiService.getVersion();
    }

    @Override
    public Flowable<AccessResponse> registration(String name, String email, String password,
                                                 String birthDate, String birthTime, String country, String deviceId,
                                                 String osType, String location) {
        return apiService.registration(name, email, password, birthDate, birthTime, country, deviceId, osType,
                location);
    }

    @Override
    public Flowable<AccessResponse> login(String email, String password) {
        return apiService.login(email, password);
    }

    @Override
    public Flowable<AccessResponse> facebookLoginPrimary(String facebookId, String name, String email, String deviceId,
                                                         String osType, String location) {
        return apiService.facebookLoginPrimary(facebookId, name, email, deviceId,
                osType, location);
    }

    @Override
    public Flowable<AccessResponse> facebookLoginSecondary(String token, String email, String birthDate, String birthTime, String country) {
        return apiService.facebookLoginSecondary(token, email, birthDate, birthTime, country);
    }

    @Override
    public Flowable<AccessResponse> forgetPassword(String email) {
        return apiService.forgetPassword(email);
    }

    @Override
    public Flowable<AccessResponse> forgetPasswordUpdate(String email, String password, String otp) {
        return apiService.forgetPasswordUpdate(email, password, otp);
    }

    @Override
    public Flowable<ProfileResponse> profileData(String animalId, String yearId) {
        return apiService.profileData(animalId, yearId);
    }

    @Override
    public Flowable<AccessResponse> mediaUploadApi(String token, RequestBody name, RequestBody birthDate, MultipartBody.Part file) {
        return apiService.mediaUploadApi(token, name, birthDate, file);
    }

    @Override
    public Flowable<AccessResponse> mediaUploadApiEmpty(String token, String name, String birthDate) {
        return apiService.mediaUploadApiEmpty(token, name, birthDate);
    }

    @Override
    public Flowable<ForecastSliderRespondse> forecastSlider() {
        return apiService.forecastSlider();
    }

    @Override
    public Flowable<BookResponse> books(String yearId) {
        return apiService.books(yearId);
    }

    @Override
    public Flowable<PurchaseResponse> bookSuccessfulPurchase(String token, String publishYear,
                                                             String orderId, String productId, String purchaseTime,
                                                             String purchaseToken) {
        return apiService.bookSuccessfulPurchase(
                token,
                publishYear,
                orderId,
                productId,
                purchaseTime,
                purchaseToken);
    }

    @Override
    public Flowable<AccessResponse> premiumSuccessfulPurchase(String token, String publishYear, String orderId,
                                                              String productId, String purchaseTime, String purchaseToken) {
        return apiService.premiumSuccessfulPurchase(
                token,
                publishYear,
                orderId,
                productId,
                purchaseTime,
                purchaseToken);
    }

    @Override
    public Flowable<AccessResponse> restoreApp(String token) {
        return apiService.restoreApp(token);
    }

    @Override
    public Flowable<AdsRespose> getAdsId() {
        return apiService.getAdsId();
    }

    @Override
    public Flowable<FcmResponse> pushFcmToken(String token, String firebaseToken) {
        return apiService.pushFcmToken(token, firebaseToken);
    }

    @Override
    public Flowable<ResponseBody> downloadFile(String url) {
        return apiService.downloadFile(url);
    }

    @Override
    public Call<ResponseBody> downloadFileRetrofit(String url) {
        return apiService.downloadFileRetrofit(url);
    }

    @Override
    public Flowable<ForecastActivityResponse> forecastActivity(String date) {
        return apiService.forecastActivity(date);
    }

    @Override
    public Flowable<ForecastMonthResponse> forecastMonth(String token, String date) {
        return apiService.forecastMonth(token, date);
    }

    @Override
    public Flowable<SliderResponse> slider() {
        return apiService.slider();
    }

    @Override
    public Flowable<SliderResponse> ghostMonth() {
        return apiService.ghostMonth();
    }

    @Override
    public Flowable<SliderResponse> flyStar() {
        return apiService.flyStar();
    }

    @Override
    public Flowable<TermsResponse> termsAndPrivacy() {
        return apiService.termsAndPrivacy();
    }

    @Override
    public Flowable<AccessResponse> logout(String token) {
        return apiService.logout(token);
    }

    @Override
    public Flowable<ForecastCalendarResponse> forecastCalender(String iconId, String yearId, String dataType) {
        return apiService.forecastCalender(iconId, yearId, dataType);
    }

    @Override
    public Flowable<CalendarResponse> calendar(String yearId) {
        return apiService.calendar(yearId);
    }

    @Override
    public Flowable<CalendarResponse> calendar(String yearId, String monthId) {
        return apiService.calendar(yearId, monthId);
    }

}

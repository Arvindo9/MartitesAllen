package com.maritesallen.almanac2020.data.remote;


import com.maritesallen.almanac2020.data.model.apis.fb.FcmResponse;
import com.maritesallen.almanac2020.data.model.apis.access.AccessResponse;
import com.maritesallen.almanac2020.data.model.apis.ads.AdsRespose;
import com.maritesallen.almanac2020.data.model.apis.books.BookResponse;
import com.maritesallen.almanac2020.data.model.apis.calender.CalendarResponse;
import com.maritesallen.almanac2020.data.model.apis.flag.FlagResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastActivityResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalendarResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.ForecastMonthResponse;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.ForecastSliderRespondse;
import com.maritesallen.almanac2020.data.model.apis.profile.ProfileResponse;
import com.maritesallen.almanac2020.data.model.apis.purchase.PurchaseResponse;
import com.maritesallen.almanac2020.data.model.apis.slider.SliderResponse;
import com.maritesallen.almanac2020.data.model.apis.terms.TermsResponse;
import com.maritesallen.almanac2020.data.model.apis.version.VersionResponse;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Author       : Arvindo Mondal
 * Created on   : 24-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class APIs implements APIService {

    private final APIService apiService;
    private final APIService apiServiceUtil;

    @Inject
//    public APIs(Retrofit retrofit, Retrofit retrofitIp) {
    public APIs(@Named("APP") Retrofit retrofit, @Named("RetrofitUtil") Retrofit retrofitUtil) {
        this.apiService = retrofit.create(APIService.class);
        this.apiServiceUtil = retrofitUtil.create(APIService.class);
    }

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
    public Flowable<AccessResponse> facebookLoginPrimary(String facebookId, String name, String email,
                                                         String deviceId, String osType, String location) {
        return apiService.facebookLoginPrimary(facebookId, name, email,
                deviceId, osType, location);
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
    public Flowable<AccessResponse> mediaUploadApi(String token, RequestBody name, RequestBody birthDate, MultipartBody.Part file) {
        return apiService.mediaUploadApi(token, name, birthDate, file);
    }

    @Override
    public Flowable<AccessResponse> mediaUploadApiEmpty(String token, String name, String birthDate) {
        return apiService.mediaUploadApiEmpty(token, name, birthDate);
    }

    @Override
    public Flowable<ProfileResponse> profileData(String animalId, String yearId) {
        return apiService.profileData(animalId, yearId);
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
    public Flowable<ResponseBody> downloadFile(String url) {
        return apiServiceUtil.downloadFile(url);
    }

    @Override
    public Call<ResponseBody> downloadFileRetrofit(String url) {
        return apiServiceUtil.downloadFileRetrofit(url);
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

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

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public interface APIService {
    /*
    @FormUrlEncoded
    @POST("api/ApplicationID/GenerateApplicationId")
    Flowable<ApplicationIdApi> getApplicationIdApi(
            @Field("DeviceId") String deviceId,
            @Field("ApplicationCode") String applicationCode
    );
    */

    @GET("api/v1/country_list")
    Flowable<FlagResponse> getFlags();

    @GET("api/v1/app-version")
    Flowable<VersionResponse> getVersion();

    @FormUrlEncoded
    @POST("api/v1/register")
    Flowable<AccessResponse> registration(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
//            @Field("c_password") String confirmPassword,
            @Field("birth_date") String birthDate,
            @Field("birth_time") String birthTime,
            @Field("country") String country,
            @Field("device_id") String deviceId,
            @Field("os_type") String osType,
            @Field("location") String location
    );

    @FormUrlEncoded
    @POST("api/v1/login")
    Flowable<AccessResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/v1/facebook_register")
    Flowable<AccessResponse> facebookLoginPrimary(
            @Field("facebookId") String facebookId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("device_id") String deviceId,
            @Field("os_type") String osType,
            @Field("location") String location
    );

    @FormUrlEncoded
    @POST("api/v1/facebook_register_step2")
    Flowable<AccessResponse> facebookLoginSecondary(
            @Header("Authorization") String token,
            @Field("email") String email,
            @Field("birth_date") String birthDate,
            @Field("birth_time") String birthTime,
            @Field("country") String country
    );

    @GET("api/v1/day_activity/{day_activity}")
    Flowable<ForecastActivityResponse> forecastActivity(
            @Path("day_activity") String date
    );

//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/v1/day_hours/{day_hours}")
    Flowable<ForecastMonthResponse> forecastMonth(
            @Header("Authorization") String token,
            @Path("day_hours") String date
    );

    @GET("api/v1/sliders")
    Flowable<SliderResponse> slider();

    @GET("api/v1/ghost-month")
    Flowable<SliderResponse> ghostMonth();

    @GET("api/v1/flying-stars")
    Flowable<SliderResponse> flyStar();

    @GET("api/v1/pages")
    Flowable<TermsResponse> termsAndPrivacy();

    @GET("api/v1/logout")
    Flowable<AccessResponse> logout(
            @Header("Authorization") String token
    );

    @GET("api/v1/forecast_calendar/{iconId}/{yearId}/{dataType}")
    Flowable<ForecastCalendarResponse> forecastCalender(
            @Path("iconId") String iconId,
            @Path("yearId") String yearId,
            @Path("dataType") String dataType);

    @GET("api/v1/calendar_duty_officers/{yearId}")
    Flowable<CalendarResponse> calendar(
            @Path("yearId") String yearId
    );

    @GET("api/v1/monthly_calendar/{yearId}/{monthId}")
    Flowable<CalendarResponse> calendar(
            @Path("yearId") String yearId,
            @Path("monthId") String monthId
    );

    @GET("api/v1/animal_forecast/{animalId}/{yearId}")
    Flowable<ProfileResponse> profileData(
            @Path("animalId") String animalId,
            @Path("yearId") String yearId);

    @Multipart
    @POST("api/v1/update-profile")
    Flowable<AccessResponse> mediaUploadApi(
            @Header("Authorization") String token,
            @Part("name") RequestBody name,
            @Part("birth_date") RequestBody birthDate,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("api/v1/update-profile")
    Flowable<AccessResponse> mediaUploadApiEmpty(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("birth_date") String birthDate
    );

    @GET("api/v1/sliders")
    Flowable<ForecastSliderRespondse> forecastSlider();

    @GET("api/v1/books/{yearId}")
    Flowable<BookResponse> books(
            @Path("yearId") String yearId
    );

    @GET
    @Streaming
    Flowable<ResponseBody> downloadFile(@Url String url);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileRetrofit(@Url String url);


    @FormUrlEncoded
    @POST("api/v1/forget_password")
    Flowable<AccessResponse> forgetPassword(
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("api/v1/update_forget_password")
    Flowable<AccessResponse> forgetPasswordUpdate(
            @Field("email") String email,
            @Field("password") String password,
            @Field("otp") String otp
    );


    @FormUrlEncoded
    @POST("api/v1/book-subscription")
    Flowable<PurchaseResponse> bookSuccessfulPurchase(
            @Header("Authorization") String token,
            @Field("subscription_year") String publishYear,
            @Field("order_id") String orderId,
//            @Field("packageName") String packageName,
            @Field("productId") String productId,
            @Field("date_of_purchase") String purchaseTime,
//            @Field("purchaseState") String purchaseState,
//            @Field("developerPayload") String developerPayload,
            @Field("purchaseToken") String purchaseToken
//            @Field("signature") String signature
    );

    @FormUrlEncoded
    @POST("api/v1/app-subscription")
    Flowable<AccessResponse> premiumSuccessfulPurchase(
            @Header("Authorization") String token,
            @Field("year_id") String publishYear,
            @Field("subscription_id") String orderId,
            @Field("productId") String productId,
            @Field("date_of_purchase") String purchaseTime,
            @Field("purchaseToken") String purchaseToken
    );

    @GET("api/v1/book-restore")
    Flowable<AccessResponse> restoreApp(
            @Header("Authorization") String token
    );

    @GET("api/v1/ad_data")
    Flowable<AdsRespose> getAdsId();

    @FormUrlEncoded
    @POST("api/v1/push_fcm_token")
    Flowable<FcmResponse> pushFcmToken(
            @Header("Authorization") String token,
            @Field("Token") String firebaseToken
    );

}

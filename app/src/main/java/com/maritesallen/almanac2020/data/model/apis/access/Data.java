package com.maritesallen.almanac2020.data.model.apis.access;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 03-12-2019
 */
public class Data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("registration_id")
    @Expose
    private String registrationId = "";
    @SerializedName("birth_date")
    @Expose
    private String birthDate = "";
    @SerializedName("birth_time")
    @Expose
    private String birthTime = "";
    @SerializedName("country")
    @Expose
    private String country = "";
    @SerializedName("is_premium")
    @Expose
    private Integer isPremium = 0;
    @SerializedName("subscription_year")
    @Expose
    private String subscriptionYear = "";
    @SerializedName("image")
    @Expose
    private String image = "";
    @SerializedName("fb_id")
    @Expose
    private String fbId = "";
    @SerializedName("device_id")
    @Expose
    private String deviceId = "";
    @SerializedName("os_type")
    @Expose
    private String osType = "";
    @SerializedName("location")
    @Expose
    private String location = "";
    @SerializedName("animal_id")
    @Expose
    private Integer animalId = 0;
    @SerializedName("status")
    @Expose
    private String status = "";
    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt = "";
    @SerializedName("created_at")
    @Expose
    private String createdAt = "";
    @SerializedName("updated_at")
    @Expose
    private String updatedAt = "";
    @SerializedName("user_animal_title")
    @Expose
    private String userAnimalTitle = "";
    @SerializedName("user_animal_image")
    @Expose
    private String userAnimalImage = "";
    @SerializedName("token")
    @Expose
    private String token = "";
    @SerializedName("user_image")
    @Expose
    private String profilePic = "";
    @SerializedName("year_id")
    @Expose
    private String yearId = "";

    @SerializedName("directFbLogin")
    @Expose
    private Boolean directFbLogin = false;


    @SerializedName("completion_status")
    @Expose
    private Integer completionStatus;

    @SerializedName(value = "books", alternate = "book")
    @Expose
    private List<BookPremium> bookPremium = null;





    @SerializedName("login_type")
    @Expose
    private Integer loginType;
    @SerializedName("password_reset_otp")
    @Expose
    private String passwordResetOtp;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("subscribe_app")
    @Expose
    private String subscribeApp;
    @SerializedName("app_subscription_price")
    @Expose
    private String appSubscriptionPrice;
    @SerializedName("app_playstoreToken")
    @Expose
    private String appPlaystoreToken;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("application_id")
    @Expose
    private String applicationId;
    @SerializedName("private_key")
    @Expose
    private String privateKey;
    @SerializedName("public_key")
    @Expose
    private String publixcKey;
    @SerializedName("drm_token")
    @Expose
    private String drm_token;
    @SerializedName("drm_projectId")
    @Expose
    private String drm_projectId;
    @SerializedName("drm_userId")
    @Expose
    private String drm_userId;
    @SerializedName("drm_project_name")
    @Expose
    private String drm_project_name;

    @SerializedName("is_updated_dob")
    @Expose
    private Integer updateDob;



    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;


    public Integer getUpdateDob() {
        return updateDob;
    }

    public void setUpdateDob(Integer updateDob) {
        this.updateDob = updateDob;
    }

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }

    public String getSubscriptionYear() {
        return subscriptionYear;
    }

    public void setSubscriptionYear(String subscriptionYear) {
        this.subscriptionYear = subscriptionYear;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAnimalImage() {
        return userAnimalImage;
    }

    public void setUserAnimalImage(String userAnimalImage) {
        this.userAnimalImage = userAnimalImage;
    }

    public String getUserAnimalTitle() {
        return userAnimalTitle;
    }

    public void setUserAnimalTitle(String userAnimalTitle) {
        this.userAnimalTitle = userAnimalTitle;
    }

    public Boolean getDirectFbLogin() {
        return directFbLogin;
    }

    public void setDirectFbLogin(Boolean directFbLogin) {
        this.directFbLogin = directFbLogin;
    }

    public Integer getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Integer completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<BookPremium> getBookPremium() {
        return bookPremium;
    }

    public void setBookPremium(List<BookPremium> bookPremium) {
        this.bookPremium = bookPremium;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getPasswordResetOtp() {
        return passwordResetOtp;
    }

    public void setPasswordResetOtp(String passwordResetOtp) {
        this.passwordResetOtp = passwordResetOtp;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getSubscribeApp() {
        return subscribeApp;
    }

    public void setSubscribeApp(String subscribeApp) {
        this.subscribeApp = subscribeApp;
    }

    public String getAppSubscriptionPrice() {
        return appSubscriptionPrice;
    }

    public void setAppSubscriptionPrice(String appSubscriptionPrice) {
        this.appSubscriptionPrice = appSubscriptionPrice;
    }

    public String getAppPlaystoreToken() {
        return appPlaystoreToken;
    }

    public void setAppPlaystoreToken(String appPlaystoreToken) {
        this.appPlaystoreToken = appPlaystoreToken;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }


    public String getDrm_token() {
        return drm_token;
    }

    public void setDrm_token(String drm_token) {
        this.drm_token = drm_token;
    }

    public String getDrm_projectId() {
        return drm_projectId;
    }

    public void setDrm_projectId(String drm_projectId) {
        this.drm_projectId = drm_projectId;
    }

    public String getDrm_userId() {
        return drm_userId;
    }

    public void setDrm_userId(String drm_userId) {
        this.drm_userId = drm_userId;
    }

    public String getDrm_project_name() {
        return drm_project_name;
    }

    public void setDrm_project_name(String drm_project_name) {
        this.drm_project_name = drm_project_name;
    }

    public String getPublixcKey() {
        return publixcKey;
    }

    public void setPublixcKey(String publixcKey) {
        this.publixcKey = publixcKey;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}

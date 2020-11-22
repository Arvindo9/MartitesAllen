package com.maritesallen.almanac2020.data.model.apis.access;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author : Arvindo Mondal
 * Created on 11-12-2019
 */
public class FacebookModel implements Parcelable {

    private String facebookId;
    private String name;
    private String email;
    private String gender;
    private String birthDate;
    private String birthTime;
    private String countryId;
    private String deviceId;
    private String location;
    private String osType;

    public FacebookModel(String fbId, String name, String email, String birthDate) {
        this.facebookId = fbId;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public FacebookModel(Parcel in) {
        name = in.readString();
        email = in.readString();
        gender = in.readString();
        birthDate = in.readString();
        birthTime = in.readString();
        countryId = in.readString();
        deviceId = in.readString();
        location = in.readString();
        osType = in.readString();
    }

    public static final Creator<FacebookModel> CREATOR = new Creator<FacebookModel>() {
        @Override
        public FacebookModel createFromParcel(Parcel in) {
            return new FacebookModel(in);
        }

        @Override
        public FacebookModel[] newArray(int size) {
            return new FacebookModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(birthDate);
        dest.writeString(birthTime);
        dest.writeString(countryId);
        dest.writeString(deviceId);
        dest.writeString(location);
        dest.writeString(osType);
    }
}

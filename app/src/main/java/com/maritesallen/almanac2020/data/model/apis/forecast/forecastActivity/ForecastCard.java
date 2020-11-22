package com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author : Arvindo Mondal
 * Created on 05-12-2019
 */
public class ForecastCard implements Parcelable{

    private final String dutyOfficer;
    private final String dutyOfficerDescription;
    private final String monthIcon;
    private final String moonStatus;
    private final String showNow;
    private final String showNowPh;

    public ForecastCard(String dutyOfficer, String dutyOfficerDescription, String monthIcon, String moonStatus,
                        String showNow, String showNowPh) {
        this.dutyOfficer = dutyOfficer;
        this.dutyOfficerDescription = dutyOfficerDescription;
        this.monthIcon = monthIcon;
        this.moonStatus = moonStatus;
        this.showNow = showNow;
        this.showNowPh = showNowPh;
    }

    public String getDutyOfficer() {
        return dutyOfficer;
    }

    public String getDutyOfficerDescription() {
        return dutyOfficerDescription;
    }

    public String getMonthIcon() {
        return monthIcon;
    }

    public String getMoonStatus() {
        return moonStatus;
    }

    public String getShowNow() {
        return showNow;
    }

    public String getShowNowPh() {
        return showNowPh;
    }

    protected ForecastCard(Parcel in) {
        dutyOfficer = in.readString();
        dutyOfficerDescription = in.readString();
        monthIcon = in.readString();
        moonStatus = in.readString();
        showNow = in.readString();
        showNowPh = in.readString();
    }

    public static final Creator<ForecastCard> CREATOR = new Creator<ForecastCard>() {
        @Override
        public ForecastCard createFromParcel(Parcel in) {
            return new ForecastCard(in);
        }

        @Override
        public ForecastCard[] newArray(int size) {
            return new ForecastCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dutyOfficer);
        dest.writeString(dutyOfficerDescription);
        dest.writeString(monthIcon);
        dest.writeString(moonStatus);
        dest.writeString(showNow);
        dest.writeString(showNowPh);
    }
}

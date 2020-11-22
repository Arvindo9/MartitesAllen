package com.maritesallen.almanac2020.data.model.apis.calender.months;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
public class April implements Parcelable {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dayName")
    @Expose
    private String dayName;
    @SerializedName("dutyOfficer")
    @Expose
    private String dutyOfficer;
    @SerializedName("moonStatus")
    @Expose
    private String moonStatus;
    @SerializedName("moonIcon")
    @Expose
    private String moonIcon;
    @SerializedName("activity")
    @Expose
    private List<Activity> activity = null;

    protected April(Parcel in) {
        date = in.readString();
        dayName = in.readString();
        dutyOfficer = in.readString();
        moonStatus = in.readString();
        moonIcon = in.readString();
    }

    public static final Creator<April> CREATOR = new Creator<April>() {
        @Override
        public April createFromParcel(Parcel in) {
            return new April(in);
        }

        @Override
        public April[] newArray(int size) {
            return new April[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDutyOfficer() {
        return dutyOfficer;
    }

    public void setDutyOfficer(String dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public String getMoonStatus() {
        return moonStatus;
    }

    public void setMoonStatus(String moonStatus) {
        this.moonStatus = moonStatus;
    }

    public String getMoonIcon() {
        return moonIcon;
    }

    public void setMoonIcon(String moonIcon) {
        this.moonIcon = moonIcon;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(dayName);
        dest.writeString(dutyOfficer);
        dest.writeString(moonStatus);
        dest.writeString(moonIcon);
    }
}

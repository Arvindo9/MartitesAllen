package com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
@Entity(tableName = "forecastActivity")
public class Data implements Parcelable {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "currentDate")
    private String  currentDate;

    @ColumnInfo(name = "moonIcon")
    @SerializedName("moonIcon")
    @Expose
    private String monthAnimal;
    @ColumnInfo(name = "conflict_month_animal")
    @SerializedName("conflict_month_animal")
    @Expose
    private String conflictMonthAnimal;
    @ColumnInfo(name = "day_animal")
    @SerializedName("day_animal")
    @Expose
    private String dayAnimal;
    @ColumnInfo(name = "day_conflict_animal")
    @SerializedName("day_conflict_animal")
    @Expose
    private String dayConflictAnimal;
    @ColumnInfo(name = "duty_officer")
    @SerializedName("duty_officer")
    @Expose
    private String dutyOfficer;
    @ColumnInfo(name = "suitable")
    @SerializedName("suitable")
    @Expose
    private List<Suitable> suitable = null;
    @ColumnInfo(name = "unsuitable")
    @SerializedName("unsuitable")
    @Expose
    private List<Unsuitable> unsuitable = null;

    @ColumnInfo(name = "activity_Lists")
    @SerializedName("activity_Lists")
    @Expose
    private List<ActivityList> activityLists = null;
    @ColumnInfo(name = "UnSuitable_activity_Lists")
    @SerializedName("UnSuitable_activity_Lists")
    @Expose
    private List<UnSuitableActivityList> unSuitableActivityLists = null;

    @ColumnInfo(name = "moonStatus")
    @SerializedName("moonStatus")
    @Expose
    private String moonStatus;
    @ColumnInfo(name = "show_now")
    @SerializedName("show_now")
    @Expose
    private String showNow;
    @ColumnInfo(name = "show_now_ph")
    @SerializedName("show_now_ph")
    @Expose
    private String showNowPh;
    @ColumnInfo(name = "day_animal_image")
    @SerializedName("day_animal_image")
    @Expose
    private String dayAnimalImage;
    @ColumnInfo(name = "duty_officer_description")
    @SerializedName("duty_officer_description")
    @Expose
    private String dutyOfficerDescription;

    @NotNull
    public String getCurrentDate() {
        return currentDate;
    }

    public String getShowNowPh() {
        return showNowPh;
    }

    public void setShowNowPh(String showNowPh) {
        this.showNowPh = showNowPh;
    }

    public void setCurrentDate(@NotNull String currentDate) {
        this.currentDate = currentDate;
    }

    public String getMonthAnimal() {
        return monthAnimal;
    }

    public void setMonthAnimal(String monthAnimal) {
        this.monthAnimal = monthAnimal;
    }

    public String getConflictMonthAnimal() {
        return conflictMonthAnimal;
    }

    public void setConflictMonthAnimal(String conflictMonthAnimal) {
        this.conflictMonthAnimal = conflictMonthAnimal;
    }

    public String getDayAnimal() {
        return dayAnimal;
    }

    public void setDayAnimal(String dayAnimal) {
        this.dayAnimal = dayAnimal;
    }

    public String getDayConflictAnimal() {
        return dayConflictAnimal;
    }

    public void setDayConflictAnimal(String dayConflictAnimal) {
        this.dayConflictAnimal = dayConflictAnimal;
    }

    public String getDutyOfficer() {
        return dutyOfficer;
    }

    public void setDutyOfficer(String dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public List<Suitable> getSuitable() {
        return suitable;
    }

    public void setSuitable(List<Suitable> suitable) {
        this.suitable = suitable;
    }

    public List<Unsuitable> getUnsuitable() {
        return unsuitable;
    }

    public void setUnsuitable(List<Unsuitable> unsuitable) {
        this.unsuitable = unsuitable;
    }

    public List<ActivityList> getActivityLists() {
        return activityLists;
    }

    public void setActivityLists(List<ActivityList> activityLists) {
        this.activityLists = activityLists;
    }

    public List<UnSuitableActivityList> getUnSuitableActivityLists() {
        return unSuitableActivityLists;
    }

    public void setUnSuitableActivityLists(List<UnSuitableActivityList> unSuitableActivityLists) {
        this.unSuitableActivityLists = unSuitableActivityLists;
    }

    public String getMoonStatus() {
        return moonStatus;
    }

    public void setMoonStatus(String moonStatus) {
        this.moonStatus = moonStatus;
    }

    public String getShowNow() {
        return showNow;
    }

    public void setShowNow(String showNow) {
        this.showNow = showNow;
    }

    public String getDayAnimalImage() {
        return dayAnimalImage;
    }

    public void setDayAnimalImage(String dayAnimalImage) {
        this.dayAnimalImage = dayAnimalImage;
    }

    public String getDutyOfficerDescription() {
        return dutyOfficerDescription;
    }

    public void setDutyOfficerDescription(String dutyOfficerDescription) {
        this.dutyOfficerDescription = dutyOfficerDescription;
    }

    public Data(){}

    protected Data(Parcel in) {
        currentDate = in.readString();
        monthAnimal = in.readString();
        conflictMonthAnimal = in.readString();
        dayAnimal = in.readString();
        dayConflictAnimal = in.readString();
        dutyOfficer = in.readString();
        suitable = in.createTypedArrayList(Suitable.CREATOR);
        unsuitable = in.createTypedArrayList(Unsuitable.CREATOR);
        activityLists = in.createTypedArrayList(ActivityList.CREATOR);
        unSuitableActivityLists = in.createTypedArrayList(UnSuitableActivityList.CREATOR);
        moonStatus = in.readString();
        showNow = in.readString();
        dayAnimalImage = in.readString();
        dutyOfficerDescription = in.readString();
        showNowPh = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currentDate);
        dest.writeString(monthAnimal);
        dest.writeString(conflictMonthAnimal);
        dest.writeString(dayAnimal);
        dest.writeString(dayConflictAnimal);
        dest.writeString(dutyOfficer);
        dest.writeTypedList(suitable);
        dest.writeTypedList(unsuitable);
        dest.writeTypedList(activityLists);
        dest.writeTypedList(unSuitableActivityLists);
        dest.writeString(moonStatus);
        dest.writeString(showNow);
        dest.writeString(dayAnimalImage);
        dest.writeString(dutyOfficerDescription);
        dest.writeString(showNowPh);
    }
}

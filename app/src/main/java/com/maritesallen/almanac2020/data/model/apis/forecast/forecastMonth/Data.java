package com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
@Entity(tableName = "forecastMonth")
public class Data implements Parcelable {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "currentDate")
    private String currentDate = "";

    @ColumnInfo(name = "is_premium")
    @SerializedName("is_premium")
    @Expose
    private Integer isPremium;

    @ColumnInfo(name = "slot_timing")
    @SerializedName("slot_timing")
    @Expose
    private String slotTiming;

    @ColumnInfo(name = "slot_aminal")
    @SerializedName("slot_aminal")
    @Expose
    private String slotAminal;

    @ColumnInfo(name = "slot_forecast")
    @SerializedName("slot_forecast")
    @Expose
    private String slotForecast;

    @ColumnInfo(name = "slot_forecast_icon")
    @SerializedName("slot_forecast_icon")
    @Expose
    private String slotForecastIcon;

    @ColumnInfo(name = "month_animal")
    @SerializedName("month_animal")
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

    @ColumnInfo(name = "month_animal_image")
    @SerializedName("month_animal_image")
    @Expose
    private String monthAnimalImage;

    @ColumnInfo(name = "month_conflict_animal_image")
    @SerializedName("month_conflict_animal_image")
    @Expose
    private String monthConflictAnimalImage;

    public Data(){}

    protected Data(Parcel in) {
        if (in.readByte() == 0) {
            isPremium = null;
        } else {
            isPremium = in.readInt();
        }
        currentDate = in.readString();
        slotTiming = in.readString();
        slotAminal = in.readString();
        slotForecast = in.readString();
        slotForecastIcon = in.readString();
        monthAnimal = in.readString();
        conflictMonthAnimal = in.readString();
        dayAnimal = in.readString();
        dayConflictAnimal = in.readString();
        monthAnimalImage = in.readString();
        monthConflictAnimalImage = in.readString();
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

    @NotNull
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(@NotNull String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSlotForecastIcon() {
        return slotForecastIcon;
    }

    public void setSlotForecastIcon(String slotForecastIcon) {
        this.slotForecastIcon = slotForecastIcon;
    }

    public Integer getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Integer isPremium) {
        this.isPremium = isPremium;
    }

    public String getSlotTiming() {
        return slotTiming;
    }

    public void setSlotTiming(String slotTiming) {
        this.slotTiming = slotTiming;
    }

    public String getSlotAminal() {
        return slotAminal;
    }

    public void setSlotAminal(String slotAminal) {
        this.slotAminal = slotAminal;
    }

    public String getSlotForecast() {
        return slotForecast;
    }

    public void setSlotForecast(String slotForecast) {
        this.slotForecast = slotForecast;
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

    public String getMonthAnimalImage() {
        return monthAnimalImage;
    }

    public void setMonthAnimalImage(String monthAnimalImage) {
        this.monthAnimalImage = monthAnimalImage;
    }

    public String getMonthConflictAnimalImage() {
        return monthConflictAnimalImage;
    }

    public void setMonthConflictAnimalImage(String monthConflictAnimalImage) {
        this.monthConflictAnimalImage = monthConflictAnimalImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (isPremium == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isPremium);
        }
        dest.writeString(currentDate);
        dest.writeString(slotTiming);
        dest.writeString(slotAminal);
        dest.writeString(slotForecast);
        dest.writeString(slotForecastIcon);
        dest.writeString(monthAnimal);
        dest.writeString(conflictMonthAnimal);
        dest.writeString(dayAnimal);
        dest.writeString(dayConflictAnimal);
        dest.writeString(monthAnimalImage);
        dest.writeString(monthConflictAnimalImage);
    }
}

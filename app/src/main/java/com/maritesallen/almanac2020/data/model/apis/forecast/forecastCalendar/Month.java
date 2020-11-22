package com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
public class Month implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Month")
    @Expose
    private String month;
    @SerializedName("MonthColor")
    @Expose
    private String monthColor;
    @SerializedName("DayColor")
    @Expose
    private String dayColor;
    @SerializedName("Days")
    @Expose
    private List<Day> days;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthColor() {
        return monthColor;
    }

    public void setMonthColor(String monthColor) {
        this.monthColor = monthColor;
    }

    public String getDayColor() {
        return dayColor;
    }

    public void setDayColor(String dayColor) {
        this.dayColor = dayColor;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Month(){}

    protected Month(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        month = in.readString();
        monthColor = in.readString();
        dayColor = in.readString();
        days = in.createTypedArrayList(Day.CREATOR);
    }

    public static final Creator<Month> CREATOR = new Creator<Month>() {
        @Override
        public Month createFromParcel(Parcel in) {
            return new Month(in);
        }

        @Override
        public Month[] newArray(int size) {
            return new Month[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(month);
        dest.writeString(monthColor);
        dest.writeString(dayColor);
        dest.writeTypedList(days);
    }
}

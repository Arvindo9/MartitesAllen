package com.maritesallen.almanac2020.data.model.apis.calender;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
public class CalendarData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name = "Months")
    @SerializedName("Months")
    @Expose
    private List<CalendarModel> months = null;

    @ColumnInfo(name = "January")
    @SerializedName("January")
    @Expose
    private List<CalendarModel> january = null;

    @ColumnInfo(name = "February")
    @SerializedName("February")
    @Expose
    private List<CalendarModel> february = null;

    @ColumnInfo(name = "April")
    @SerializedName("April")
    @Expose
    private List<CalendarModel> april = null;

    @ColumnInfo(name = "March")
    @SerializedName("March")
    @Expose
    private List<CalendarModel> march = null;

    @ColumnInfo(name = "May")
    @SerializedName("May")
    @Expose
    private List<CalendarModel> may = null;

    @ColumnInfo(name = "June")
    @SerializedName("June")
    @Expose
    private List<CalendarModel> june = null;

    @ColumnInfo(name = "July")
    @SerializedName("July")
    @Expose
    private List<CalendarModel> july = null;

    @ColumnInfo(name = "August")
    @SerializedName("August")
    @Expose
    private List<CalendarModel> august = null;

    @ColumnInfo(name = "September")
    @SerializedName("September")
    @Expose
    private List<CalendarModel> september = null;

    @ColumnInfo(name = "October")
    @SerializedName("October")
    @Expose
    private List<CalendarModel> october = null;

    @ColumnInfo(name = "November")
    @SerializedName("November")
    @Expose
    private List<CalendarModel> november = null;

    @ColumnInfo(name = "December")
    @SerializedName("December")
    @Expose
    private List<CalendarModel> december = null;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public List<CalendarModel> getMonths() {
        return months;
    }

    public void setMonths(List<CalendarModel> months) {
        this.months = months;
    }

    public List<CalendarModel> getJanuary() {
        return january;
    }

    public void setJanuary(List<CalendarModel> january) {
        this.january = january;
    }

    public List<CalendarModel> getFebruary() {
        return february;
    }

    public void setFebruary(List<CalendarModel> february) {
        this.february = february;
    }

    public List<CalendarModel> getApril() {
        return april;
    }

    public void setApril(List<CalendarModel> april) {
        this.april = april;
    }

    public List<CalendarModel> getMarch() {
        return march;
    }

    public void setMarch(List<CalendarModel> march) {
        this.march = march;
    }

    public List<CalendarModel> getMay() {
        return may;
    }

    public void setMay(List<CalendarModel> may) {
        this.may = may;
    }

    public List<CalendarModel> getJune() {
        return june;
    }

    public void setJune(List<CalendarModel> june) {
        this.june = june;
    }

    public List<CalendarModel> getJuly() {
        return july;
    }

    public void setJuly(List<CalendarModel> july) {
        this.july = july;
    }

    public List<CalendarModel> getAugust() {
        return august;
    }

    public void setAugust(List<CalendarModel> august) {
        this.august = august;
    }

    public List<CalendarModel> getSeptember() {
        return september;
    }

    public void setSeptember(List<CalendarModel> september) {
        this.september = september;
    }

    public List<CalendarModel> getOctober() {
        return october;
    }

    public void setOctober(List<CalendarModel> october) {
        this.october = october;
    }

    public List<CalendarModel> getNovember() {
        return november;
    }

    public void setNovember(List<CalendarModel> november) {
        this.november = november;
    }

    public List<CalendarModel> getDecember() {
        return december;
    }

    public void setDecember(List<CalendarModel> december) {
        this.december = december;
    }

    protected CalendarData(Parcel in) {
        _id = in.readLong();
    }

    public static final Creator<CalendarData> CREATOR = new Creator<CalendarData>() {
        @Override
        public CalendarData createFromParcel(Parcel in) {
            return new CalendarData(in);
        }

        @Override
        public CalendarData[] newArray(int size) {
            return new CalendarData[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
    }
}

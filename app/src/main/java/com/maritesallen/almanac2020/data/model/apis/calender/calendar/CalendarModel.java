package com.maritesallen.almanac2020.data.model.apis.calender.calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
@Entity(tableName = "calendar")
public class CalendarModel{

    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    private String date;

    @ColumnInfo(name = "dateNo")
    @SerializedName("dateNo")
    @Expose
    private String dateNo;

    @ColumnInfo(name = "dayName")
    @SerializedName("dayName")
    @Expose
    private String dayName;

    @ColumnInfo(name = "dutyOfficer")
    @SerializedName("dutyOfficer")
    @Expose
    private String dutyOfficer;

    @ColumnInfo(name = "moonStatus")
    @SerializedName("moonStatus")
    @Expose
    private String moonStatus;

    @ColumnInfo(name = "dateInt")
    @SerializedName("dateInt")
    @Expose
    private Integer dateInt;

    @ColumnInfo(name = "moonIcon")
    @SerializedName("moonIcon")
    @Expose
    private String moonIcon;

    @ColumnInfo(name = "activity")
    @SerializedName("activity")
    @Expose
    private List<Activity> activity = null;


    @ColumnInfo(name = "monthId")
    @SerializedName(value = "MonthId", alternate = "monthId")
    @Expose
    private Integer monthId;

    @ColumnInfo(name = "month")
    @SerializedName(value = "Month", alternate = "month")
    @Expose
    private String month;

    @ColumnInfo(name = "image")
    @SerializedName(value = "Image")
    @Expose
    private String monthCalendarPic;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    private boolean monthCalendar = false;

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

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDateNo() {
        return dateNo;
    }

    public void setDateNo(String dateNo) {
        this.dateNo = dateNo;
    }

    public boolean isMonthCalendar() {
        return monthCalendar;
    }

    public void setMonthCalendar(boolean monthCalendar) {
        this.monthCalendar = monthCalendar;
    }

    public String getMonthCalendarPic() {
        return monthCalendarPic;
    }

    public void setMonthCalendarPic(String monthCalendarPic) {
        this.monthCalendarPic = monthCalendarPic;
    }

    public Integer getDateInt() {
        return dateInt;
    }

    public void setDateInt(Integer dateInt) {
        this.dateInt = dateInt;
    }
}

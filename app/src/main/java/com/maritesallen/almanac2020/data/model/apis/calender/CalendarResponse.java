package com.maritesallen.almanac2020.data.model.apis.calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
public class CalendarResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CalendarData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CalendarData getCalendarData() {
        return data;
    }

    public void setCalendarData(CalendarData data) {
        this.data = data;
    }

}

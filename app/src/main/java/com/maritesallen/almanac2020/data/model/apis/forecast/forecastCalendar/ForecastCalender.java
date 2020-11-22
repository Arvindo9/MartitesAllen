package com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
public class ForecastCalender {

    private Integer id;
    private String month;
    private String monthColor;
    private final int type;
    private String day;

    public ForecastCalender(int type, String day) {
        this.type = type;
        this.day = day;
    }

    public ForecastCalender(int type, Integer id, String month, String monthColor) {
        this.type = type;
        this.id = id;
        this.month = month;
        this.monthColor = monthColor;
    }

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

    public int getType() {
        return type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

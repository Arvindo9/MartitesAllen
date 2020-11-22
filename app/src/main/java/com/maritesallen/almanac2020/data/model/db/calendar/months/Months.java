package com.maritesallen.almanac2020.data.model.db.calendar.months;

public class Months {

    private int id;
    private String month;
    private String year;

    public Months(int id, String month, String year){
        this.id = id;
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.maritesallen.almanac2020.data.model.apis.calender;

public class Calenders {
    private String dayText;
    private String sign;

    public Calenders(String dayText, String sign){
        this.dayText = dayText;
        this.sign = sign;
    }

    public String getDayText() {
        return dayText;
    }

    public void setDayText(String dayText) {
        this.dayText = dayText;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

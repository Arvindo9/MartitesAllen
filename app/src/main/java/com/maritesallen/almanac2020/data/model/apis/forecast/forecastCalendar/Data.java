package com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
@Entity(
        tableName = "forecastCalendar",
        primaryKeys = {"iconId", "dataType"}
)
public class Data {

    @NotNull
    @ColumnInfo(name = "iconId")
    private String iconId = "";

    @NotNull
    @ColumnInfo(name = "dataType")
    private String dataType = "";

    @ColumnInfo(name = "yearId")
    private String yearId;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "Months")
    @SerializedName("Months")
    @Expose
    private List<Month> months = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Month> getMonths() {
        return months;
    }

    public void setMonths(List<Month> months) {
        this.months = months;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }
}

package com.maritesallen.almanac2020.data.model.db.flag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "flag")
public class Flag {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String countryCode = "";

    @ColumnInfo(name = "country_name")
    @SerializedName("country_name")
    @Expose
    private String countryName;

    @Ignore
    public Flag(String countryName){
        this.countryName = countryName;
    }

    public Flag(){}

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @NotNull
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(@NotNull String countryCode) {
        this.countryCode = countryCode;
    }
}

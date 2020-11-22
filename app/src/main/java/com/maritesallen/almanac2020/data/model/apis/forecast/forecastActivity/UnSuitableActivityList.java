package com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
public class UnSuitableActivityList implements Parcelable {
    @SerializedName("title")
    @Expose
    private String title = "";
    @SerializedName("hex_color")
    @Expose
    private String hexColor = "";
    @SerializedName("icon_image")
    @Expose
    private String iconImage = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }


    protected UnSuitableActivityList(Parcel in) {
        hexColor = in.readString();
        iconImage = in.readString();
        title = in.readString();
    }

    public static final Creator<UnSuitableActivityList> CREATOR = new Creator<UnSuitableActivityList>() {
        @Override
        public UnSuitableActivityList createFromParcel(Parcel in) {
            return new UnSuitableActivityList(in);
        }

        @Override
        public UnSuitableActivityList[] newArray(int size) {
            return new UnSuitableActivityList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hexColor);
        dest.writeString(iconImage);
        dest.writeString(title);
    }
}

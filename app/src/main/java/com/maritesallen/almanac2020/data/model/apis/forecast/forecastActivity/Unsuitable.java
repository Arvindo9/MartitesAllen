package com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class Unsuitable implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("hex_color")
    @Expose
    private String hexColor;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("year")
    @Expose
    private String year;

    protected Unsuitable(Parcel in) {
        id = in.readString();
        title = in.readString();
        hexColor = in.readString();
        image = in.readString();
        year = in.readString();
    }

    public static final Creator<Unsuitable> CREATOR = new Creator<Unsuitable>() {
        @Override
        public Unsuitable createFromParcel(Parcel in) {
            return new Unsuitable(in);
        }

        @Override
        public Unsuitable[] newArray(int size) {
            return new Unsuitable[size];
        }
    };

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(hexColor);
        dest.writeString(image);
        dest.writeString(year);
    }
}

package com.maritesallen.almanac2020.data.model.apis.slider;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Author : Arvindo Mondal
 * Created on 11-01-2020
 */
@Entity(tableName = "flyStarSlider")
public class Slider implements Parcelable {

    @ColumnInfo(name = "primaryKey")
    private Long _id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @NotNull
    @PrimaryKey()
    @ColumnInfo(name = "image")
    @SerializedName("image")
    @Expose
    private String image = "";

    public Slider(){}

    protected Slider(Parcel in) {
        title = in.readString();
        image = Objects.requireNonNull(in.readString());
    }

    public static final Creator<Slider> CREATOR = new Creator<Slider>() {
        @Override
        public Slider createFromParcel(Parcel in) {
            return new Slider(in);
        }

        @Override
        public Slider[] newArray(int size) {
            return new Slider[size];
        }
    };

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        this.image = image;
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
        dest.writeString(title);
        dest.writeString(image);
    }
}

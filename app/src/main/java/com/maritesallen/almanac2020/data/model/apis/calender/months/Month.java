package com.maritesallen.almanac2020.data.model.apis.calender.months;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
public class Month  implements Parcelable {
    @SerializedName("MonthId")
    @Expose
    private Integer monthId;
    @SerializedName("Month")
    @Expose
    private String month;

    protected Month(Parcel in) {
        if (in.readByte() == 0) {
            monthId = null;
        } else {
            monthId = in.readInt();
        }
        month = in.readString();
    }

    public static final Creator<Month> CREATOR = new Creator<Month>() {
        @Override
        public Month createFromParcel(Parcel in) {
            return new Month(in);
        }

        @Override
        public Month[] newArray(int size) {
            return new Month[size];
        }
    };

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
        if (monthId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(monthId);
        }
        dest.writeString(month);
    }
}

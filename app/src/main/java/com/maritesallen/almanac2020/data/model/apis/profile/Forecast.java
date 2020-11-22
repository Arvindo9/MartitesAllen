package com.maritesallen.almanac2020.data.model.apis.profile;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 13-12-2019
 */
@Entity(tableName = "profileData")
public class Forecast implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long _id;

    @ColumnInfo(name = "romance")
    @SerializedName("romance")
    @Expose
    private Romance romance;

    @ColumnInfo(name = "wealth")
    @SerializedName("wealth")
    @Expose
    private Wealth wealth;

    @ColumnInfo(name = "health")
    @SerializedName("health")
    @Expose
    private Health health;

    @ColumnInfo(name = "career")
    @SerializedName("career")
    @Expose
    private Career career;



    @ColumnInfo(name = "animal")
    @SerializedName("animal")
    @Expose
    private Animal animal;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Romance getRomance() {
        return romance;
    }

    public void setRomance(Romance romance) {
        this.romance = romance;
    }

    public Wealth getWealth() {
        return wealth;
    }

    public void setWealth(Wealth wealth) {
        this.wealth = wealth;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }


    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


    public Forecast(){}

    protected Forecast(Parcel in) {
        _id = in.readLong();
        romance = in.readParcelable(Romance.class.getClassLoader());
        wealth = in.readParcelable(Wealth.class.getClassLoader());
        health = in.readParcelable(Health.class.getClassLoader());
        career = in.readParcelable(Career.class.getClassLoader());
        animal = in.readParcelable(Animal.class.getClassLoader());
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

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
        dest.writeLong(_id);
        dest.writeParcelable(romance, flags);
        dest.writeParcelable(wealth, flags);
        dest.writeParcelable(health, flags);
        dest.writeParcelable(career, flags);
        dest.writeParcelable(animal, flags);
    }
}

package com.maritesallen.almanac2020.core.dialogs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-08-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class DialogModel implements Parcelable{
    private int _id;
    private String id;
    private String data;
    private String remark;

    public DialogModel(int _id, String data){
        this._id = _id;
        this.data = data;
    }

    public DialogModel(String id, String data){
        this.id = id;
        this.data = data;
    }

    public DialogModel(String id, String data, String remark){
        this.id = id;
        this.data = data;
        this.remark = remark;
    }

    public int get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getRemark() {
        return remark;
    }

    protected DialogModel(Parcel in) {
        _id = in.readInt();
        id = in.readString();
        data = in.readString();
        remark = in.readString();
    }

    public static final Creator<DialogModel> CREATOR = new Creator<DialogModel>() {
        @Override
        public DialogModel createFromParcel(Parcel in) {
            return new DialogModel(in);
        }

        @Override
        public DialogModel[] newArray(int size) {
            return new DialogModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(id);
        dest.writeString(data);
        dest.writeString(remark);
    }
}

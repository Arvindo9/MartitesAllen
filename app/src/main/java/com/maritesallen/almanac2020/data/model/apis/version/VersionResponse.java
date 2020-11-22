package com.maritesallen.almanac2020.data.model.apis.version;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author       : Arvindo Mondal
 * Created date : 13-08-2019
 * Email        : arvindo@aiprog.in
 * Company      : AIPROG
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 * Website      : www.aiprog.in
 */
public class VersionResponse {
    @SerializedName("android_app_version")
    @Expose
    private String androidAppVersion;

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }
}

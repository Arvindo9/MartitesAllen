package com.maritesallen.almanac2020.data.model.apis.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 09-01-2020
 */
public class Data {

    @SerializedName("add_id")
    @Expose
    private String adsId;

    @SerializedName("add_unit_id")
    @Expose
    private String adsUnitId;

    public String getAdsId() {
        return adsId;
    }

    public void setAdsId(String adsId) {
        this.adsId = adsId;
    }

    public String getAdsUnitId() {
        return adsUnitId;
    }

    public void setAdsUnitId(String adsUnitId) {
        this.adsUnitId = adsUnitId;
    }
}

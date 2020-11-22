package com.maritesallen.almanac2020.data.model.apis.terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
public class Data {
    @SerializedName("page")
    @Expose
    private List<Terms> terms = null;

    public List<Terms> getTerms() {
        return terms;
    }

    public void setTerms(List<Terms> terms) {
        this.terms = terms;
    }
}

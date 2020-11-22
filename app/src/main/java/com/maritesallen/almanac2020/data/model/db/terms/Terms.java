package com.maritesallen.almanac2020.data.model.db.terms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
@Entity(tableName = "termsPrivacy")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long _id;

    @SerializedName("page_title")
    @Expose
    private String pageTitle;
    @SerializedName("page_content")
    @Expose
    private String pageContent;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
